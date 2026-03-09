package cz.games.lp.backend.orchestration;

import cz.games.lp.backend.orchestration.enums.ProductionStates;
import cz.games.lp.backend.service.commonservices.ConsoleServices;
import cz.games.lp.backend.service.commonservices.GamePartsServices;
import cz.games.lp.gamecore.actions.ProduceReport;
import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.components.Player;
import cz.games.lp.gamecore.components.enums.Sources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Component
public class ProductionOrchestrator {

    private static final String SOURCE_CHOOSER_TITLE = "Zvolte si zdroj produkce:";
    private static final String REPEAT_PRODUCTION_CHOICE_TITLE = "Zvolte kartu, kterou chcete produkovat znovu:";
    private static final String FACTION_CARD = "Frakční karta";
    private static final String COMMON_CARD = "Běžná karta";
    private final GamePartsServices gamePartsServices;
    private final ConsoleServices consoleServices;
    private List<ProduceReport> produceReports;
    private Player player;
    private UUID roomID;
    private UUID playerID;
    private Runnable methodAfterProduction;

    public ProductionOrchestrator(GamePartsServices gamePartsServices, ConsoleServices consoleServices) {
        this.gamePartsServices = gamePartsServices;
        this.consoleServices = consoleServices;
    }

    public void performProduction(UUID roomID, UUID playerID, Runnable methodAfterProduction) {
        log.debug("performProduction");
        this.roomID = roomID;
        this.playerID = playerID;
        this.methodAfterProduction = methodAfterProduction;
        player = gamePartsServices.getPlayerService().getPlayer(roomID, playerID);
        doProduce(ProductionStates.FACTION_CARDS_PRODUCE);
    }

    private void doProduce(ProductionStates productionState) {
        log.debug("doProduce");
        switch (productionState) {
            case FACTION_CARDS_PRODUCE -> {
                produceReports = gamePartsServices.getProductionService().produceFactionCards(roomID).get(playerID);
                printAllCardsProduction(0, () -> doProduce(ProductionStates.DEALS_PRODUCE));
            }
            case DEALS_PRODUCE -> {
                produceReports = gamePartsServices.getProductionService().produceDeals(roomID).get(playerID);
                printDeals(0);
                doProduce(ProductionStates.FACTION_BOARD_PRODUCE);
            }
            case FACTION_BOARD_PRODUCE -> {
                gamePartsServices.getProductionService().produceFactionBoard(roomID).get(playerID).getSource().forEach(source -> consoleServices.getConsolePrinter().factionBoardProduction(source));
                doProduce(ProductionStates.COMMON_CARDS_PRODUCE);
            }
            case COMMON_CARDS_PRODUCE -> {
                produceReports = gamePartsServices.getProductionService().produceCommonCards(roomID).get(playerID);
                printAllCardsProduction(0, methodAfterProduction);
            }
        }
    }

    public void printAllCardsProduction(int produceReportIndex, Runnable nextProduction) {
        log.debug("produceAllCards");
        if (produceReportIndex == produceReports.size()) {
            nextProduction.run();
            return;
        }
        printOneCardProduction(produceReports.get(produceReportIndex), () -> printAllCardsProduction(produceReportIndex + 1, nextProduction));
    }

    private void printOneCardProduction(ProduceReport produceReport, Runnable nextProduction) {
        log.debug("produceOneCard");
        if (!produceReport.getOrSource().isEmpty()) {
            orEffectFilled(produceReport, () -> printOneCardProduction(produceReport, nextProduction));
            return;
        }
        if (produceReport.isProduceAnotherProduction()) {
            produceReport.getAllBuiltProductions().forEach(cardID -> consoleServices.getConsoleUI().putAction(cardID, () -> printOneCardProduction(gamePartsServices.getProductionService().produceFromSingleCard(cardID, roomID, playerID), nextProduction)));
            consoleServices.getConsoleUI().showActionChoices(REPEAT_PRODUCTION_CHOICE_TITLE);
        } else if (Sources.CARD.equals(produceReport.getSource().getFirst())) {
            cardSourceProduce(produceReport, nextProduction);
        } else {
            consoleServices.getConsolePrinter().cardProduction(produceReport);
            nextProduction.run();
        }
    }

    private void cardSourceProduce(ProduceReport produceReport, Runnable nextProduction) {
        consoleServices.getConsoleUI().putAction(FACTION_CARD, () -> {
            gamePartsServices.getCardService().dealFactionCardToPlayer(player);
            consoleServices.getConsolePrinter().cardProduction(produceReport);
            nextProduction.run();
        });
        consoleServices.getConsoleUI().putAction(COMMON_CARD, () -> {
            gamePartsServices.getCardService().dealCommonCardToPlayer(player, gamePartsServices.getGameService().getRoom(roomID));
            consoleServices.getConsolePrinter().cardProduction(produceReport);
            nextProduction.run();
        });
        consoleServices.getConsoleUI().showActionChoices(SOURCE_CHOOSER_TITLE);
    }

    private void cardSourceProduce(int index, Runnable nextProduction) {
        consoleServices.getConsoleUI().putAction(FACTION_CARD, () -> {
            gamePartsServices.getCardService().dealFactionCardToPlayer(player);
            consoleServices.getConsolePrinter().dealProduceInfo(produceReports.get(index));
            nextProduction.run();
        });
        consoleServices.getConsoleUI().putAction(COMMON_CARD, () -> {
            gamePartsServices.getCardService().dealCommonCardToPlayer(player, gamePartsServices.getGameService().getRoom(roomID));
            consoleServices.getConsolePrinter().dealProduceInfo(produceReports.get(index));
            nextProduction.run();
        });
        consoleServices.getConsoleUI().showActionChoices(SOURCE_CHOOSER_TITLE);
    }

    private void orEffectFilled(ProduceReport produceReport, Runnable printOneCardProductionAction) {
        log.debug("orEffectFilled");
        if (Sources.FACTION_CARD.equals(produceReport.getSource().getFirst()) && Sources.FACTION_CARD.equals(produceReport.getOrSource().getFirst())) {
            IntStream.range(0, 2).forEach(i -> {
                int cardNumber = player.getFactionCardDeck().getCards().get(i);
                Card card = gamePartsServices.getCardService().getNewPlayerCard(player, cardNumber);
                consoleServices.getConsoleUI().putAction(card.toString(), () -> {
                    gamePartsServices.getCardService().dealCardToPlayer(player, cardNumber, true);
                    produceReport.getOrSource().clear();
                    printOneCardProductionAction.run();
                });
            });
        } else {
            consoleServices.getConsoleUI().putAction("" + produceReport.getSource(), () -> {
                gamePartsServices.getSourceService().giveSourcesToPlayer(player, produceReport.getSource());
                produceReport.getOrSource().clear();
                printOneCardProductionAction.run();
            });
            consoleServices.getConsoleUI().putAction("" + produceReport.getOrSource(), () -> {
                gamePartsServices.getSourceService().giveSourcesToPlayer(player, produceReport.getOrSource());
                produceReport.getSource().clear();
                produceReport.getSource().addAll(produceReport.getOrSource());
                produceReport.getOrSource().clear();
                printOneCardProductionAction.run();
            });
        }
        consoleServices.getConsoleUI().showActionChoices(SOURCE_CHOOSER_TITLE);
    }

    private void printDeals(int dealIndex) {
        log.debug("produceDeal");
        if (dealIndex == produceReports.size()) {
            doProduce(ProductionStates.FACTION_BOARD_PRODUCE);
            return;
        }
        if (Sources.CARD.equals(produceReports.get(dealIndex).getDeal())) {
            cardSourceProduce(dealIndex, () -> printDeals(dealIndex + 1));
        } else {
            consoleServices.getConsolePrinter().dealProduceInfo(produceReports.get(dealIndex));
            printDeals(dealIndex + 1);
        }
    }
}
