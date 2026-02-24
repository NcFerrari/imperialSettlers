package cz.games.lp.backend.orchestration;

import cz.games.lp.backend.orchestration.enums.ConsoleStates;
import cz.games.lp.backend.service.unifiedservices.ConsoleServices;
import cz.games.lp.backend.service.unifiedservices.GamePartsServices;
import cz.games.lp.gamecore.components.enums.CardCategories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class ConsoleOrchestrator {

    private static final String ACTION_CHOOSER_TITLE = "Zvolte akci:";
    private static final String FACTION_CHOOSER_TITLE = "Vyberte si frakci:";
    private final ConsoleServices consoleServices;
    private final GamePartsServices gamePartsServices;
    private final ProductionOrchestrator productionOrchestrator;
    private UUID roomID;
    private UUID playerID;

    public ConsoleOrchestrator(ConsoleServices consoleServices, GamePartsServices gamePartsServices, ProductionOrchestrator productionOrchestrator) {
        this.consoleServices = consoleServices;
        this.gamePartsServices = gamePartsServices;
        this.productionOrchestrator = productionOrchestrator;
    }

    public void startConsoleGame(UUID roomID, UUID playerID) {
        log.debug("startConsoleGame");
        this.roomID = roomID;
        this.playerID = playerID;
        log.info("room ID = {}", roomID);
        log.info("player ID = {}", playerID);
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
                consoleServices.getConsoleUI().putAction(factionType.name(), () -> {
                    gamePartsServices.getPlayerService().initPlayerAndUpdateGameRoom(roomID, playerID, factionType);
                    gamePartsServices.getFactionService().getFactionActions().resetFactionSelection(gamePartsServices.getGameService().getRoom(roomID).getRemainingFactions());
                    playGame(ConsoleStates.SET_NEW_GAME);
                }));
        consoleServices.getConsoleUI().showActionChoices(FACTION_CHOOSER_TITLE);
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
        consoleServices.getConsoleUI().putAction("Rozdat pocatecni karty", () -> {
            gamePartsServices.getGameService().dealFirstCardsToAllPlayers(roomID);
            playGame(ConsoleStates.PERFORM_LOOKOUT_PHASE);
        });
        consoleServices.getConsoleUI().showActionChoices(ACTION_CHOOSER_TITLE);
    }

    private void performLookoutPhase() {
        log.debug("performLookoutPhase");
        consoleServices.getConsoleUI().putAction("Zahajit fazi rozhledu", () -> {
            gamePartsServices.getGameService().performLookoutPhase(roomID);
            playGame(ConsoleStates.PERFORM_PRODUCTION_PHASE);
        });
        consoleServices.getConsoleUI().showActionChoices(ACTION_CHOOSER_TITLE);
    }

    public void performProductionPhase() {
        log.debug("performProductionPhase");
        consoleServices.getConsoleUI().putAction("Zahajit fazi produkce", () -> productionOrchestrator.performProduction(roomID, playerID, () -> playGame(ConsoleStates.PERFORM_ACTIONS_PHASE)));
        consoleServices.getConsoleUI().showActionChoices(ACTION_CHOOSER_TITLE);
    }

    private void mockData() {
        log.debug("mockData");
        gamePartsServices.getCardService().cardMap()
                .entrySet()
                .stream()
                .filter(entry -> CardCategories.COMMON_PRODUCTION.equals(entry.getValue().getCardCategory()))
                .filter(entry -> entry.getValue().getCardId().contains("com"))
                .forEach(entry -> {
                    System.out.println(entry.getValue());
                    gamePartsServices.getPlayerService().getPlayer(roomID, playerID).getBuiltLocations().get(CardCategories.COMMON_PRODUCTION).add(entry.getValue());
                });
    }

    private void performActionsPhase() {
        log.debug("performActionsPhase");
        consoleServices.getConsoleUI().showActionChoices("action");
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
}