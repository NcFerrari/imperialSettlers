package cz.games.lp.backend.orchestration;

import cz.games.lp.backend.orchestration.enums.ProductionStates;
import cz.games.lp.backend.service.agregates.ConsoleServices;
import cz.games.lp.backend.service.agregates.GamePartsServices;
import cz.games.lp.gamecore.actions.ProduceChoice;
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
    private final GamePartsServices gamePartsServices;
    private final ConsoleServices consoleServices;
    private List<ProduceChoice> produceChoices;
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
                produceChoices = gamePartsServices.getProductionService().produceFactionCards(roomID).get(playerID);
                produceOneCard(0, () -> doProduce(ProductionStates.DEALS_PRODUCE));
            }
            case DEALS_PRODUCE -> {
                produceChoices = gamePartsServices.getProductionService().produceDeals(roomID).get(playerID);
                produceDeal(0);
            }
            case FACTION_BOARD_PRODUCE -> {
                gamePartsServices.getProductionService().produceFactionBoard(roomID).get(playerID).getSource().forEach(source -> consoleServices.getConsolePrinter().factionBoardProduction(source));
                doProduce(ProductionStates.COMMON_CARDS_PRODUCE);
            }
            case COMMON_CARDS_PRODUCE -> {
                produceChoices = gamePartsServices.getProductionService().produceCommonCards(roomID).get(playerID);
                produceOneCard(0, methodAfterProduction);
            }
        }
    }

    private void produceOneCard(int produceChoiceIndex, Runnable nextProduction) {
        log.debug("produceOneCard");
        if (produceChoiceIndex == produceChoices.size()) {
            nextProduction.run();
            return;
        }
        ProduceChoice produceChoice = produceChoices.get(produceChoiceIndex);
        if (produceChoice.getOrSource() == null || produceChoice.getOrSource().isEmpty()) {
            if (produceChoices.get(produceChoiceIndex).isProduceAnotherProduction()) {

            } else if (Sources.CARD.equals(produceChoices.get(produceChoiceIndex).getSource().getFirst())) {
                cardSourceProduce(produceChoiceIndex, () -> produceOneCard(produceChoiceIndex + 1, nextProduction));
            } else {
                consoleServices.getConsolePrinter().cardProduction(produceChoice.getCardID(), produceChoice.getSource());
                produceOneCard(produceChoiceIndex + 1, nextProduction);
            }
        } else {
            orEffectFilled(produceChoice, () -> produceOneCard(produceChoiceIndex, nextProduction));
        }
    }

    private void orEffectFilled(ProduceChoice produceChoice, Runnable runnable) {
        log.debug("orEffectFilled");
        if (Sources.FACTION_CARD.equals(produceChoice.getSource().getFirst()) && Sources.FACTION_CARD.equals(produceChoice.getOrSource().getFirst())) {
            IntStream.range(0, 2).forEach(i -> {
                int cardNumber = player.getFactionCardDeck().getCards().get(i);
                Card card = gamePartsServices.getCardService().getNewPlayerCard(player, cardNumber);
                consoleServices.getConsoleUI().putAction(card.toString(), () -> {
                    gamePartsServices.getCardService().dealCardToPlayer(player, cardNumber, true);
                    produceChoice.getOrSource().clear();
                    runnable.run();
                });
            });
        } else {
            consoleServices.getConsoleUI().putAction("" + produceChoice.getSource(), () -> {
                gamePartsServices.getSourceService().giveSourcesToPlayer(player, produceChoice.getSource());
                produceChoice.getOrSource().clear();
                runnable.run();
            });
            consoleServices.getConsoleUI().putAction("" + produceChoice.getOrSource(), () -> {
                gamePartsServices.getSourceService().giveSourcesToPlayer(player, produceChoice.getOrSource());
                produceChoice.getSource().clear();
                produceChoice.getSource().addAll(produceChoice.getOrSource());
                produceChoice.getOrSource().clear();
                runnable.run();
            });
        }
        consoleServices.getConsoleUI().showActionChoices(SOURCE_CHOOSER_TITLE);
    }

    private void produceDeal(int dealIndex) {
        log.debug("produceDeal");
        if (dealIndex == produceChoices.size()) {
            doProduce(ProductionStates.FACTION_BOARD_PRODUCE);
            return;
        }
        if (Sources.CARD.equals(produceChoices.get(dealIndex).getDeal())) {
            cardSourceProduce(dealIndex, () -> produceDeal(dealIndex + 1));
        } else {
            consoleServices.getConsolePrinter().dealProduceInfo(produceChoices.get(dealIndex));
            produceDeal(dealIndex + 1);
        }
    }

    private void cardSourceProduce(int index, Runnable nextProduction) {
        consoleServices.getConsoleUI().putAction("Frakční karta", () -> {
            gamePartsServices.getCardService().dealFactionCardToPlayer(player);
            consoleServices.getConsolePrinter().dealProduceInfo(produceChoices.get(index));
            nextProduction.run();
        });
        consoleServices.getConsoleUI().putAction("Běžná karta", () -> {
            gamePartsServices.getCardService().dealCommonCardToPlayer(player, gamePartsServices.getGameService().getRoom(roomID));
            consoleServices.getConsolePrinter().dealProduceInfo(produceChoices.get(index));
            nextProduction.run();
        });
        consoleServices.getConsoleUI().showActionChoices(SOURCE_CHOOSER_TITLE);
    }
}
