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

    public ProductionOrchestrator(GamePartsServices gamePartsServices, ConsoleServices consoleServices) {
        this.gamePartsServices = gamePartsServices;
        this.consoleServices = consoleServices;
    }

    public void performProduction(UUID roomID, UUID playerID) {
        log.debug("performProduction");
        this.roomID = roomID;
        this.playerID = playerID;
        player = gamePartsServices.getPlayerService().getPlayer(roomID, playerID);
        doProduce(ProductionStates.FACTION_CARDS_PRODUCE);
    }

    private void doProduce(ProductionStates productionState) {
        log.debug("doProduce");
        switch (productionState) {
            case FACTION_CARDS_PRODUCE -> {
                produceChoices = gamePartsServices.getProductionService().produceFactionCards(roomID).get(playerID);
                produceOneCard(0);
            }
            case DEALS_PRODUCE -> {
                produceChoices = gamePartsServices.getProductionService().produceDeals(roomID).get(playerID);
                produceDeal(0);
            }
            case FACTION_BOARD_PRODUCE -> {
                gamePartsServices.getProductionService().produceFactionBoard(roomID).get(playerID).source().forEach(source -> log.info("Frakční deska produkuje {}", source));
                doProduce(ProductionStates.COMMON_CARDS_PRODUCE);
            }
            case COMMON_CARDS_PRODUCE -> consoleServices.getConsoleUI().showActionChoices("a");
        }
    }

    private void produceOneCard(int produceChoiceIndex) {
        log.debug("produceOneCard");
        if (produceChoiceIndex == produceChoices.size()) {
            doProduce(ProductionStates.DEALS_PRODUCE);
            return;
        }
        ProduceChoice produceChoice = produceChoices.get(produceChoiceIndex);
        if (produceChoice.orSource().isEmpty()) {
            log.info("karta {} produkuje: {}", produceChoice.cardID(), produceChoice.source());
            produceOneCard(produceChoiceIndex + 1);
        } else {
            orEffectFilled(produceChoice, () -> produceOneCard(produceChoiceIndex));
        }
    }

    private void orEffectFilled(ProduceChoice produceChoice, Runnable runnable) {
        log.debug("orEffectFilled");
        if (Sources.FACTION_CARD.equals(produceChoice.source().getFirst()) && Sources.FACTION_CARD.equals(produceChoice.orSource().getFirst())) {
            IntStream.range(0, 2).forEach(i -> {
                int cardNumber = player.getFactionCardDeck().getCards().get(i);
                Card card = gamePartsServices.getCardService().getNewPlayerCard(player, cardNumber);
                consoleServices.getConsoleUI().putAction(card.toString(), () -> {
                    gamePartsServices.getCardService().dealCardToPlayer(player, cardNumber, true);
                    produceChoice.orSource().clear();
                    runnable.run();
                });
            });
        } else {
            consoleServices.getConsoleUI().putAction("" + produceChoice.source(), () -> {
                gamePartsServices.getSourceService().giveSourcesToPlayer(player, produceChoice.source());
                produceChoice.orSource().clear();
                runnable.run();
            });
            consoleServices.getConsoleUI().putAction("" + produceChoice.orSource(), () -> {
                gamePartsServices.getSourceService().giveSourcesToPlayer(player, produceChoice.orSource());
                produceChoice.source().clear();
                produceChoice.source().addAll(produceChoice.orSource());
                produceChoice.orSource().clear();
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
        if (Sources.CARD.equals(produceChoices.get(dealIndex).deal())) {
            consoleServices.getConsoleUI().putAction("Frakční karta", () -> {
                gamePartsServices.getCardService().dealFactionCardToPlayer(player);
                consoleServices.getConsolePrinter().dealProduceInfo(produceChoices.get(dealIndex));
                produceDeal(dealIndex + 1);
            });
            consoleServices.getConsoleUI().putAction("Běžná karta", () -> {
                gamePartsServices.getCardService().dealCommonCardToPlayer(player, gamePartsServices.getGameService().getRoom(roomID));
                consoleServices.getConsolePrinter().dealProduceInfo(produceChoices.get(dealIndex));
                produceDeal(dealIndex + 1);
            });
            consoleServices.getConsoleUI().showActionChoices(SOURCE_CHOOSER_TITLE);
        } else {
            consoleServices.getConsolePrinter().dealProduceInfo(produceChoices.get(dealIndex));
            produceDeal(dealIndex + 1);
        }
    }
}
