package cz.games.lp.backend.orchestration;

import cz.games.lp.backend.infrstructure.console.ConsoleStates;
import cz.games.lp.backend.service.agregates.ConsoleServices;
import cz.games.lp.backend.service.agregates.GamePartsServices;
import cz.games.lp.gamecore.components.Player;
import cz.games.lp.gamecore.components.enums.ProductionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class ConsoleOrchestrator {

    private static final String ACTION_TITLE = "Zvolte akci:";
    private final Map<String, Runnable> actionsMap = new LinkedHashMap<>();
    private final ConsoleServices consoleServices;
    private final GamePartsServices gamePartsServices;
    private UUID uuid;

    public ConsoleOrchestrator(ConsoleServices consoleServices, GamePartsServices gamePartsServices) {
        this.consoleServices = consoleServices;
        this.gamePartsServices = gamePartsServices;
    }

    public void startConsoleGame(UUID uuid) {
        log.debug("startConsoleGame");
        this.uuid = uuid;
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
        consoleServices.getConsoleUI().showActionChoices();
    }

    private void performProductionPhase() {
        log.debug("performProductionPhase");
        fillMap("Zahajit fazi produkce",
                () -> {
                    ProductionStatus productionStatus = gamePartsServices.getGameService().performProductionPhase();
                    if (ProductionStatus.ENDS.equals(productionStatus)) {
                        playGame(ConsoleStates.PERFORM_ACTIONS_PHASE);
                        return;
                    }
                    playGame(ConsoleStates.PERFORM_PRODUCTION_PHASE);
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
        gamePartsServices.getCardService().generateNewCommonCardDeck(uuid);
//        gamePartsServices.getPlayerService().getPlayers().forEach(Player::newGame);
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
            gamePartsServices.getFactionService().resetFactionSelection(uuid);
            consoleServices.getConsoleUI().clearCommonActions();
            playGame(ConsoleStates.SELECT_FACTIONS);
        });
    }

    private void selectFactionsForAllPlayers() {
        log.debug("prepareCurrentPlayer");
        gamePartsServices.getFactionService().getRemainingFactions(uuid)
                .forEach(faction -> actionsMap.put(faction.name(), () -> {
                    gamePartsServices.getGameService().actionsWhenChooseFaction(uuid, faction);
                    if (gamePartsServices.getPlayerService().allPlayersHaveBeenProcessed(uuid)) {
                        playGame(ConsoleStates.SET_NEW_GAME);
                        return;
                    }
                    playGame(ConsoleStates.SELECT_FACTIONS);
                }));
        addAction("Vyberte si frakci:");
    }

    private void fillMap(String actionTitle, Runnable runnable) {
        log.debug("fillMap");
        actionsMap.put(actionTitle, runnable);
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