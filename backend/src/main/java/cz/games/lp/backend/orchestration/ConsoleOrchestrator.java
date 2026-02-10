package cz.games.lp.backend.orchestration;

import cz.games.lp.backend.infrstructure.console.ConsoleStates;
import cz.games.lp.backend.service.agregates.ConsoleServices;
import cz.games.lp.backend.service.agregates.GamePartsServices;
import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.components.enums.CardCategories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class ConsoleOrchestrator {

    private static final String ACTION_CHOOSER_TITLE = "Zvolte akci:";
    private static final String FACTION_CHOOSER_TITLE = "Vyberte si frakci:";
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
        gamePossibleChoices.put("Zahajit fazi produkce", () -> gamePartsServices.getProductionService().performProductionPhase(roomID).get(playerID).forEach(produceResult -> {
            if (produceResult.orSource() == null) {
                log.info("karta {} produkuje: {}", produceResult.cardID(), produceResult.source());
            } else {
                addAction(ACTION_CHOOSER_TITLE);
            }
        }));
        addAction(ACTION_CHOOSER_TITLE);
    }

    private void mockData() {
        List<Card> cards = gamePartsServices.getCardService().getCardActions().getCardCatalog().cardMap().values()
                .stream()
                .filter(card -> CardCategories.FACTION_PRODUCTION.equals(card.getCardCategory())
                        && card.getCardId().contains(gamePartsServices.getGameService().getRoom(roomID).getCurrentPlayer().getFaction().getFactionType().getCardPrefix().getCardPrefix()))
                .map(card -> card.toBuilder().build())
                .sorted(Comparator.comparing(Card::getCardId))
                .toList();
        cards.getFirst().setSamurai(true);
        cards.get(2).setSamurai(true);
        gamePartsServices.getGameService().getRoom(roomID).getCurrentPlayer().getBuiltLocations().put(CardCategories.FACTION_PRODUCTION, cards);
        gamePartsServices.getGameService().getRoom(roomID).getCurrentPlayer().getBuiltLocations().get(CardCategories.COMMON_PROPERTIES).add(gamePartsServices.getCardService().getCardActions().getCardCatalog().cardMap().get("com078").toBuilder().build());
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