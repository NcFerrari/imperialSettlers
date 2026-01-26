package cz.games.lp.backend.orchestration;

import cz.games.lp.backend.infrstructure.console.ConsolePrinter;
import cz.games.lp.backend.infrstructure.console.ConsoleStates;
import cz.games.lp.backend.infrstructure.console.ConsoleUI;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.backend.service.agregates.GameServices;
import cz.games.lp.gamecore.components.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class ConsoleOrchestrator {

    private static final String ACTION_TITLE = "Zvolte akci:";
    private final Map<String, Runnable> actionsMap = new LinkedHashMap<>();
    private final ConsoleUI consoleUI;
    private final ConsolePrinter consolePrinter;
    private final GameServices gameServices;
    private final PlayerService playerService;

    public ConsoleOrchestrator(ConsoleUI consoleUI, ConsolePrinter consolePrinter, GameServices gameServices, PlayerService playerService) {
        this.consoleUI = consoleUI;
        this.consolePrinter = consolePrinter;
        this.gameServices = gameServices;
        this.playerService = playerService;
    }

    public void startConsoleGame() {
        log.debug("startConsoleGame");
        consoleUI.executeConsoleInputLoop();
        playGame(ConsoleStates.START_GAME);
    }

    private void playGame(ConsoleStates state) {
        log.debug("playGame");
        switch (state) {
            case START_GAME -> startGame();
            case SELECT_FACTION -> prepareCurrentPlayer();
            case SET_NEW_GAME -> newGame();
            case DEAL_FIRST_CARDS -> dealFirstCards();
            case PERFORM_LOOKOUT_PHASE -> performLookoutPhase();
            case PERFORM_PRODUCTION_PHASE -> performProductionPhase();
        }
    }

    private void performProductionPhase() {
        log.debug("performProductionPhase");
        fillMap("Zahajit fazi produkce", ConsoleStates.PERFORM_PRODUCTION_PHASE,
                () -> gameServices.getPlayerService().performProductionPhase());
        addAction(ACTION_TITLE);
    }

    private void performLookoutPhase() {
        log.debug("performLookoutPhase");
        fillMap("Zahajit fazi rozhledu", ConsoleStates.PERFORM_PRODUCTION_PHASE,
                () -> gameServices.getPlayerService().performLookoutPhase());
        addAction(ACTION_TITLE);
    }

    private void dealFirstCards() {
        log.debug("dealFirstCards");
        fillMap("Rozdat pocatecni karty", ConsoleStates.PERFORM_LOOKOUT_PHASE,
                () -> gameServices.getPlayerService().dealFirstCards());
        addAction(ACTION_TITLE);
    }

    private void newGame() {
        log.debug("newGame");
//        gameManager.newGame();
        playerService.getPlayers().forEach(Player::newGame);
        initCommonActions();
        playGame(ConsoleStates.DEAL_FIRST_CARDS);
    }

    private void initCommonActions() {
        log.debug("initCommonActions");
        consoleUI.clearCommonActions();
        consoleUI.addCommonAction("Zobraz aktuální stav", () -> {
            consolePrinter.showCurrentStats();
            consoleUI.showActionChoices();
        });
        consoleUI.addCommonAction("Zobraz karty", () -> {
            consolePrinter.showCards();
            consoleUI.showActionChoices();
        });
        consoleUI.addCommonAction("Začni novou hru", () -> {
            gameServices.getFactionService().resetFactionSelection();
            playGame(ConsoleStates.SELECT_FACTION);
        });
    }

    private void prepareCurrentPlayer() {
        log.debug("prepareCurrentPlayer");
        consoleUI.clearCommonActions();
        gameServices.getFactionService().getRemainingFactions()
                .forEach(faction -> actionsMap.put(faction.name(), () -> {
                    gameServices.getFactionService().selectFactionForCurrentPlayer(faction);
                    gameServices.getPlayerService().setUpSourcesForCurrentPlayer();
                    if (playerService.getCurrentPlayer().equals(playerService.getFirstPlayer())) {
                        playGame(ConsoleStates.SET_NEW_GAME);
                        return;
                    }
                    playGame(ConsoleStates.SELECT_FACTION);
                }));
        addAction("Vyberte si frakci:");
        playerService.nextPlayer();
    }

    private void fillMap(String actionTitle, ConsoleStates state, Runnable runnable) {
        log.debug("fillMap");
        actionsMap.put(actionTitle, () -> {
            runnable.run();
            playGame(state);
        });
    }

    private void addAction(String title) {
        log.debug("addAction");
        consoleUI.addActions(actionsMap);
        consoleUI.showActionChoices(title);
        actionsMap.clear();
    }

    private void startGame() {
        log.debug("startGame");
        consolePrinter.initMessage();
        playGame(ConsoleStates.SELECT_FACTION);
    }
}