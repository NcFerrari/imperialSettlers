package cz.games.lp.backend.orchestration;

import cz.games.lp.backend.infrstructure.console.ConsolePrinter;
import cz.games.lp.backend.infrstructure.console.ConsoleStates;
import cz.games.lp.backend.infrstructure.console.ConsoleUI;
import cz.games.lp.backend.service.agregates.GameService;
import cz.games.lp.gamecore.GameManager;
import cz.games.lp.gamecore.components.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ConsoleOrchestrator {

    private final ConsoleUI consoleUI;
    private final ConsolePrinter consolePrinter;
    private final GameService gameService;
    private final GameManager gameManager;
    private ConsoleStates currentConsoleState;

    public ConsoleOrchestrator(ConsoleUI consoleUI, ConsolePrinter consolePrinter, GameService gameService, GameManager gameManager) {
        this.consoleUI = consoleUI;
        this.consolePrinter = consolePrinter;
        this.gameService = gameService;
        this.gameManager = gameManager;
    }

    public void startConsoleGame() {
        log.debug("startConsoleGame");
        consoleUI.executeConsoleInputLoop();
        currentConsoleState = ConsoleStates.START_GAME;
        playGame();
    }

    private void playGame() {
        log.debug("playGame");
        switch (currentConsoleState) {
            case START_GAME -> startGame();
            case PREPARE_PLAYERS -> preparePlayers();
            case SET_NEW_GAME -> newGame();
            case DEAL_FIRST_CARDS -> dealFirstCards();
            case PERFORM_LOOKOUT_PHASE -> performLookoutPhase();
            case PERFORM_PRODUCTION_PHASE -> performProductionPhase();
        }
    }

    private void performProductionPhase() {
        log.debug("performProductionPhase");
        Map<String, Runnable> map = new HashMap<>();
        map.put("Zahajit fazi produkce", () -> {
            gameService.getPlayerService().performProductionPhase();
            playGame();
        });
        consoleUI.addActions(map);
        consoleUI.showActionChoices();
    }

    private void performLookoutPhase() {
        log.debug("performLookoutPhase");
        Map<String, Runnable> map = new HashMap<>();
        map.put("Zahajit fazi rozhledu", () -> {
            gameService.getPlayerService().perfrormLookoutPhase();
            currentConsoleState = ConsoleStates.PERFORM_PRODUCTION_PHASE;
            playGame();
        });
        consoleUI.addActions(map);
        consoleUI.showActionChoices();
    }

    private void dealFirstCards() {
        log.debug("dealFirstCards");
        Map<String, Runnable> map = new HashMap<>();
        map.put("Rozdat pocatecni karty", () -> {
            gameService.getPlayerService().dealFirstCards();
            currentConsoleState = ConsoleStates.PERFORM_LOOKOUT_PHASE;
            playGame();
        });
        consoleUI.addActions(map);
        consoleUI.showActionChoices("Zvolte akci:");
    }

    private void newGame() {
        log.debug("newGame");
        gameManager.newGame();
        gameManager.getPlayers().forEach(Player::newGame);
        currentConsoleState = ConsoleStates.DEAL_FIRST_CARDS;
        playGame();
    }

    private void preparePlayers() {
        log.debug("preparePlayers");
        Player currentPlayer = gameManager.getCurrentPlayer();
        consoleUI.clearCommonActions();
        selectFaction(currentPlayer);
        gameManager.nextPlayer();
        if (currentPlayer.equals(gameManager.getFirstPlayer())) {
            initCommonActions();
            currentConsoleState = ConsoleStates.SET_NEW_GAME;
        }
    }

    private void selectFaction(Player player) {
        log.debug("selectFaction");
        Map<String, Runnable> map = gameService.getFactionService().getRemainingFactions()
                .stream()
                .collect(Collectors.toMap(
                        Enum::name,
                        faction -> () -> {
                            gameService.getFactionService().selectFaction(player, faction);
                            gameService.getPlayerService().setUpSourcesForPlayer(player);
                            playGame();
                        },
                        (runnable, runnable2) -> runnable,
                        LinkedHashMap::new
                ));
        consoleUI.addActions(map);
        consoleUI.showActionChoices("Vyberte si frakci:");
    }

    private void startGame() {
        log.debug("startGame");
        consolePrinter.initMessage();
        currentConsoleState = ConsoleStates.PREPARE_PLAYERS;
        playGame();
    }

    private void initCommonActions() {
        log.debug("initCommonActions");
        consoleUI.addCommonAction("Zobraz aktuální stav", () -> {
            consolePrinter.showCurrentStats();
            consoleUI.showActionChoices();
        });
        consoleUI.addCommonAction("Zobraz karty", () -> {
            consolePrinter.showCards();
            consoleUI.showActionChoices();
        });
        consoleUI.addCommonAction("Začni novou hru", () -> {
            gameService.getFactionService().resetFactionSelection();
            currentConsoleState = ConsoleStates.PREPARE_PLAYERS;
            playGame();
        });
    }
}