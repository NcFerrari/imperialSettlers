package cz.games.lp.backend.orchestration;

import cz.games.lp.backend.infrstructure.console.ConsoleStates;
import cz.games.lp.backend.service.agregates.ConsoleServices;
import cz.games.lp.backend.service.agregates.GamePartsServices;
import cz.games.lp.gamecore.components.enums.FactionTypes;
import cz.games.lp.gamecore.components.enums.ProductionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
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

    public ConsoleOrchestrator(ConsoleServices consoleServices, GamePartsServices gamePartsServices) {
        this.consoleServices = consoleServices;
        this.gamePartsServices = gamePartsServices;
    }

    public void startConsoleGame(UUID roomID) {
        log.debug("startConsoleGame");
        this.roomID = roomID;
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
        gamePartsServices.getGameService().getRemainingFactions(roomID).forEach(factionType -> fillMap(factionType.name(), () -> processAfterSelectFaction(factionType)));
        addAction(FACTION_CHOOSER_TITLE);
    }

    private void processAfterSelectFaction(FactionTypes factionType) {
        UUID playerID = gamePartsServices.getGameService().getRoom(roomID).getCurrentPlayer().getPlayerID();
        gamePartsServices.getPlayerService().initPlayerAndUpdateGameRoom(roomID, playerID, factionType);
        gamePartsServices.getFactionService().removeFromChoice(gamePartsServices.getGameService().getRemainingFactions(roomID), factionType);
        gamePartsServices.getGameService().getGameRoomActions().nextPlayer(roomID);
        if (gamePartsServices.getPlayerService().
                allPlayersHaveBeenProcessed(roomID)) {
            playGame(ConsoleStates.SET_NEW_GAME);
            gamePartsServices.getFactionService().resetFactionSelection(gamePartsServices.getGameService().getRemainingFactions(roomID));
            return;
        }
        playGame(ConsoleStates.SELECT_FACTIONS);
    }

    private void newGame() {
        log.debug("newGame");
        gamePartsServices.getGameService().newGame(roomID);
        gamePartsServices.getPlayerService().newGameForAllPlayers(roomID);
        initCommonActions();
        playGame(ConsoleStates.DEAL_FIRST_CARDS);
    }

    private void dealFirstCards() {
        log.debug("dealFirstCards");
        fillMap("Rozdat pocatecni karty", () -> {
            gamePartsServices.getGameService().dealFirstCardsToAllPlayers(roomID);
            playGame(ConsoleStates.PERFORM_LOOKOUT_PHASE);
        });
        addAction(ACTION_CHOOSER_TITLE);
    }

    private void performLookoutPhase() {
        log.debug("performLookoutPhase");
        fillMap("Zahajit fazi rozhledu", () -> {
            gamePartsServices.getGameService().performLookoutPhase(roomID);
            playGame(ConsoleStates.PERFORM_PRODUCTION_PHASE);
        });
        addAction(ACTION_CHOOSER_TITLE);
    }

    private void performProductionPhase() {
        log.debug("performProductionPhase");
        fillMap("Zahajit fazi produkce", () -> {
            ProductionStatus productionStatus = gamePartsServices.getGameService().performProductionPhase();
            if (ProductionStatus.ENDS.equals(productionStatus)) {
                playGame(ConsoleStates.PERFORM_ACTIONS_PHASE);
                return;
            }
            playGame(ConsoleStates.PERFORM_PRODUCTION_PHASE);
        });
        addAction(ACTION_CHOOSER_TITLE);
    }

    private void performActionsPhase() {
        log.debug("performActionsPhase");
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

    private void fillMap(String actionTitle, Runnable runnable) {
        log.debug("fillMap");
        gamePossibleChoices.put(actionTitle, runnable);
    }

    private void addAction(String title) {
        log.debug("addAction");
        consoleServices.getConsoleUI().addActions(gamePossibleChoices);
        consoleServices.getConsoleUI().showActionChoices(title);
        gamePossibleChoices.clear();
    }
}