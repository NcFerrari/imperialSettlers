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
        doProduce(ProductionStates.FACTION_CARDS_PRODUCE);
    }

    private void doProduce(ProductionStates productionState) {
        log.debug("doProduce");
        switch (productionState) {
            case FACTION_CARDS_PRODUCE ->
                    produceOneCard(0, gamePartsServices.getProductionService().performProductionPhase(roomID).get(playerID));
            case DEALS_PRODUCE -> produceDeals();
//            case FACTION_BOARD_PRODUCE ->
//            case COMMON_CARDS_PRODUCE ->
        }
    }

    private void produceOneCard(int produceChoiceIndex, List<ProduceChoice> produceChoices) {
        log.debug("produceOneCard");
        if (produceChoiceIndex == produceChoices.size()) {
            doProduce(ProductionStates.DEALS_PRODUCE);
            return;
        }
        ProduceChoice produceChoice = produceChoices.get(produceChoiceIndex);
        if (produceChoice.orSource().isEmpty()) {
            log.info("karta {} produkuje: {}", produceChoice.cardID(), produceChoice.source());
            produceOneCard(produceChoiceIndex + 1, produceChoices);
        } else {
            orEffectFilled(produceChoice, () -> produceOneCard(produceChoiceIndex, produceChoices));
        }
    }

    private void orEffectFilled(ProduceChoice produceChoice, Runnable runnable) {
        log.debug("orEffectFilled");
        Player player = gamePartsServices.getPlayerService().getPlayer(roomID, playerID);
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

    //TODO: separate loop into recursion method
    private void produceDeals() {
        log.debug("produceDeals");
        gamePartsServices.getProductionService().produceDeals(roomID).get(playerID).forEach(produceChoice -> {
            if (Sources.CARD.equals(produceChoice.deal())) {
                Player player = gamePartsServices.getPlayerService().getPlayer(roomID, playerID);
                consoleServices.getConsoleUI().putAction("Frakční karta", () -> {
                    gamePartsServices.getCardService().dealFactionCardToPlayer(player);
                    consoleServices.getConsolePrinter().dealProduceInfo(produceChoice);
                });
                consoleServices.getConsoleUI().putAction("Běžná karta", () -> {
                    gamePartsServices.getCardService().dealCommonCardToPlayer(player, gamePartsServices.getGameService().getRoom(roomID));
                    consoleServices.getConsolePrinter().dealProduceInfo(produceChoice);
                });
                consoleServices.getConsoleUI().showActionChoices(SOURCE_CHOOSER_TITLE);
            } else {
                consoleServices.getConsolePrinter().dealProduceInfo(produceChoice);
            }
        });
    }
}
