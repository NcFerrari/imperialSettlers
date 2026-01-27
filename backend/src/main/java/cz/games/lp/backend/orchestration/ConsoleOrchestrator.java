package cz.games.lp.backend.orchestration;

import cz.games.lp.backend.infrstructure.console.ConsoleStates;
import cz.games.lp.backend.service.agregates.ActionServices;
import cz.games.lp.backend.service.agregates.ConsoleServices;
import cz.games.lp.backend.service.agregates.GamePartsServices;
import cz.games.lp.common.enums.FactionTypes;
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
    private final ConsoleServices consoleServices;
    private final GamePartsServices gamePartsServices;
    private final ActionServices actionServices;

    public ConsoleOrchestrator(ConsoleServices consoleServices, GamePartsServices gamePartsServices, ActionServices actionServices) {
        this.consoleServices = consoleServices;
        this.gamePartsServices = gamePartsServices;
        this.actionServices = actionServices;
    }

    public void startConsoleGame() {
        log.debug("startConsoleGame");
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

    private void performActionsPhase() {
        log.debug("performActionsPhase");
    }

    private void performProductionPhase() {
        log.debug("performProductionPhase");
        fillMap("Zahajit fazi produkce",
                () -> {
                    gamePartsServices.getGameService().performProductionPhase();
                    playGame(ConsoleStates.PERFORM_ACTIONS_PHASE);
                });
        addAction(ACTION_TITLE);
    }

    private void performLookoutPhase() {
        log.debug("performLookoutPhase");
        fillMap("Zahajit fazi rozhledu",
                () -> {
                    gamePartsServices.getGameService().performLookoutPhase();
                    playGame(ConsoleStates.PERFORM_PRODUCTION_PHASE);
                });
        addAction(ACTION_TITLE);
    }

    private void dealFirstCards() {
        log.debug("dealFirstCards");
        fillMap("Rozdat pocatecni karty",
                () -> {
                    gamePartsServices.getCardService().dealFirstCardsToAllPlayers();
                    playGame(ConsoleStates.PERFORM_LOOKOUT_PHASE);
                });
        addAction(ACTION_TITLE);
    }

    private void newGame() {
        log.debug("newGame");
        gamePartsServices.getGameService().newGame();
        gamePartsServices.getPlayerService().getPlayers().forEach(Player::newGame);
        initCommonActions();
        playGame(ConsoleStates.DEAL_FIRST_CARDS);
    }

    private void initCommonActions() {
        log.debug("initCommonActions");
        consoleServices.getConsoleUI().clearCommonActions();
        consoleServices.getConsoleUI().addCommonAction("Zobraz aktuální stav", () -> {
            consoleServices.getConsolePrinter().showCurrentStats();
            consoleServices.getConsoleUI().showActionChoices();
        });
        consoleServices.getConsoleUI().addCommonAction("Zobraz karty", () -> {
            consoleServices.getConsolePrinter().showCards();
            consoleServices.getConsoleUI().showActionChoices();
        });
        consoleServices.getConsoleUI().addCommonAction("Začni novou hru", () -> {
            gamePartsServices.getFactionService().resetFactionSelection();
            consoleServices.getConsoleUI().clearCommonActions();
            playGame(ConsoleStates.SELECT_FACTIONS);
        });
    }

    private void selectFactionsForAllPlayers() {
        log.debug("prepareCurrentPlayer");
        gamePartsServices.getFactionService().getRemainingFactions()
                .forEach(faction -> actionsMap.put(faction.name(), () -> actionsWhenChooseFaction(faction)));
        addAction("Vyberte si frakci:");
        gamePartsServices.getPlayerService().nextPlayer();
    }

    private void actionsWhenChooseFaction(FactionTypes faction) {
        gamePartsServices.getFactionService().selectFactionForCurrentPlayer(faction);
        gamePartsServices.getPlayerService().setUpSourcesForCurrentPlayer();
        if (gamePartsServices.getPlayerService().allPlayersHaveBeenProcessed()) {
            playGame(ConsoleStates.SET_NEW_GAME);
            return;
        }
        playGame(ConsoleStates.SELECT_FACTIONS);
    }

    private void fillMap(String actionTitle, Runnable runnable) {
        log.debug("fillMap");
        actionsMap.put(actionTitle, runnable::run);
    }

    private void addAction(String title) {
        log.debug("addAction");
        consoleServices.getConsoleUI().addActions(actionsMap);
        consoleServices.getConsoleUI().showActionChoices(title);
        actionsMap.clear();
    }

    private void startGame() {
        log.debug("startGame");
        consoleServices.getConsolePrinter().initMessage();
        playGame(ConsoleStates.SELECT_FACTIONS);
    }
}