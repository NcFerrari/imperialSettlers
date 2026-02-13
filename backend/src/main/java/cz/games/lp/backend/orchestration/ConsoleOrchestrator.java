package cz.games.lp.backend.orchestration;

import cz.games.lp.backend.infrastructure.console.ConsoleStates;
import cz.games.lp.backend.service.agregates.ConsoleServices;
import cz.games.lp.backend.service.agregates.GamePartsServices;
import cz.games.lp.gamecore.actions.ProduceChoice;
import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.components.Player;
import cz.games.lp.gamecore.components.enums.CardCategories;
import cz.games.lp.gamecore.components.enums.Sources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Component
public class ConsoleOrchestrator {

    private static final String ACTION_CHOOSER_TITLE = "Zvolte akci:";
    private static final String FACTION_CHOOSER_TITLE = "Vyberte si frakci:";
    private static final String SOURCE_CHOOSER_TITLE = "Zvolte si zdroj produkce:";
    private final Map<String, Runnable> gamePossibleChoices = new LinkedHashMap<>();
    private final ConsoleServices consoleServices;
    private final GamePartsServices gamePartsServices;
    private UUID roomID;
    private UUID playerID;

    public ConsoleOrchestrator(ConsoleServices consoleServices, GamePartsServices gamePartsServices) {
        this.consoleServices = consoleServices;
        this.gamePartsServices = gamePartsServices;
    }

    public void startConsoleGame(UUID roomID, UUID playerID) {
        log.debug("startConsoleGame");
        this.roomID = roomID;
        this.playerID = playerID;
        consoleServices.getConsoleUI().executeConsoleInputLoop();
        playGame(ConsoleStates.START_GAME);
    }

    private void playGame(ConsoleStates state) {
        log.debug("playGame");
        switch (state) {
            case START_GAME -> startGame();
            case SELECT_FACTIONS -> selectFactionsForAllPlayers();
            case SET_NEW_GAME -> newGame();
            case DEAL_FIRST_CARDS -> dealFirstCards();
            case PERFORM_LOOKOUT_PHASE -> performLookoutPhase();
            case PERFORM_PRODUCTION_PHASE -> performProductionPhase();
            case PERFORM_ACTIONS_PHASE -> performActionsPhase();
        }
    }

    private void startGame() {
        log.debug("startGame");
        consoleServices.getConsolePrinter().initMessage();
        playGame(ConsoleStates.SELECT_FACTIONS);
    }

    private void selectFactionsForAllPlayers() {
        log.debug("selectFactionsForAllPlayers");
        gamePartsServices.getPlayerService().resetAllPlayersForSelectingFaction(roomID);
        gamePartsServices.getGameService().getRemainingFactions(roomID).forEach(factionType ->
                gamePossibleChoices.put(factionType.name(), () -> {
                    gamePartsServices.getPlayerService().initPlayerAndUpdateGameRoom(roomID, playerID, factionType);
                    gamePartsServices.getFactionService().getFactionActions().resetFactionSelection(gamePartsServices.getGameService().getRoom(roomID).getRemainingFactions());
                    playGame(ConsoleStates.SET_NEW_GAME);
                }));
        addAction(FACTION_CHOOSER_TITLE);
    }

    private void newGame() {
        log.debug("newGame");
        gamePartsServices.getGameService().newGame(roomID);
        gamePartsServices.getPlayerService().newGameForAllPlayers(roomID);
        initCommonActions();
        mockData();
        playGame(ConsoleStates.DEAL_FIRST_CARDS);
    }

    private void dealFirstCards() {
        log.debug("dealFirstCards");
        gamePossibleChoices.put("Rozdat pocatecni karty", () -> {
            gamePartsServices.getGameService().dealFirstCardsToAllPlayers(roomID);
            playGame(ConsoleStates.PERFORM_LOOKOUT_PHASE);
        });
        addAction(ACTION_CHOOSER_TITLE);
    }

    private void performLookoutPhase() {
        log.debug("performLookoutPhase");
        gamePossibleChoices.put("Zahajit fazi rozhledu", () -> {
            gamePartsServices.getGameService().performLookoutPhase(roomID);
            playGame(ConsoleStates.PERFORM_PRODUCTION_PHASE);
        });
        addAction(ACTION_CHOOSER_TITLE);
    }

    private void performProductionPhase() {
        log.debug("performProductionPhase");
        gamePossibleChoices.put("Zahajit fazi produkce", () -> produceOneCard(0, gamePartsServices.getProductionService().performProductionPhase(roomID).get(playerID)));
        addAction(ACTION_CHOOSER_TITLE);
    }

    private void produceDeals() {
        log.debug("produceDeals");
        addAction(ACTION_CHOOSER_TITLE);
    }

    private void produceOneCard(int produceChoiceIndex, List<ProduceChoice> produceChoices) {
        log.debug("produceOneCard");
        if (produceChoiceIndex == produceChoices.size()) {
            produceDeals();
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
                gamePossibleChoices.put(card.toString(), () -> {
                    gamePartsServices.getCardService().dealCardToPlayer(player, cardNumber, true);
                    produceChoice.orSource().clear();
                    runnable.run();
                });
            });
        } else {
            gamePossibleChoices.put("" + produceChoice.source(), () -> {
                gamePartsServices.getSourceService().giveSourcesToPlayer(player, produceChoice.source());
                produceChoice.orSource().clear();
                runnable.run();
            });
            gamePossibleChoices.put("" + produceChoice.orSource(), () -> {
                gamePartsServices.getSourceService().giveSourcesToPlayer(player, produceChoice.orSource());
                produceChoice.source().clear();
                produceChoice.source().addAll(produceChoice.orSource());
                produceChoice.orSource().clear();
                runnable.run();
            });
        }
        addAction(SOURCE_CHOOSER_TITLE);
    }

    private void mockData() {
        log.debug("mockData");
        List<Card> cards = gamePartsServices.getCardService().getCardActions().getCardCatalog().cardMap().values()
                .stream()
                .filter(card -> CardCategories.FACTION_PRODUCTION.equals(card.getCardCategory())
                        && card.getCardId().contains(gamePartsServices.getGameService().getRoom(roomID).getCurrentPlayer().getFaction().getFactionType().getCardPrefix().getCardPrefix()))
                .map(card -> card.toBuilder().build())
                .sorted(Comparator.comparing(Card::getCardId))
                .toList();
        gamePartsServices.getGameService().getRoom(roomID).getCurrentPlayer().getBuiltLocations().put(CardCategories.FACTION_PRODUCTION, cards);
    }

    private void performActionsPhase() {
        log.debug("performActionsPhase");
        log.info(":)");
    }

    private void initCommonActions() {
        log.debug("initCommonActions");
        consoleServices.getConsoleUI().clearCommonActions();
        consoleServices.getConsoleUI().addCommonAction("Zobraz aktuální stav", () -> {
            consoleServices.getConsolePrinter().showCurrentStats(gamePartsServices.getGameService().getRoom(roomID));
            consoleServices.getConsoleUI().showActionChoices();
        });
        consoleServices.getConsoleUI().addCommonAction("Zobraz karty", () -> {
            consoleServices.getConsolePrinter().showCards(gamePartsServices.getGameService().getRoom(roomID));
            consoleServices.getConsoleUI().showActionChoices();
        });
        consoleServices.getConsoleUI().addCommonAction("Nová hra", () -> {
            consoleServices.getConsoleUI().clearCommonActions();
            playGame(ConsoleStates.SET_NEW_GAME);
        });
        consoleServices.getConsoleUI().addCommonAction("Nová hra i s výběrem frakce", () -> {
            consoleServices.getConsoleUI().clearCommonActions();
            playGame(ConsoleStates.SELECT_FACTIONS);
        });
    }

    private void addAction(String title) {
        log.debug("addAction");
        consoleServices.getConsoleUI().addActions(gamePossibleChoices);
        consoleServices.getConsoleUI().showActionChoices(title);
        gamePossibleChoices.clear();
    }
}