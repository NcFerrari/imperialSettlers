package cz.games.lp.backend.engine.consolegame;

import cz.games.lp.backend.engine.GameEngine;
import cz.games.lp.common.enums.Factions;
import cz.games.lp.gamecore.service.GameDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsoleManager {

    private final Factions[] factions = new Factions[]{Factions.BARBARIAN_F, Factions.BARBARIAN_M, Factions.JAPAN_F, Factions.JAPAN_M, Factions.ROMAN_F, Factions.ROMAN_M, Factions.EGYPT_F, Factions.EGYPT_M};
    private final ConsoleOutputs consoleOutputs;
    private final GameEngine gameEngine;
    private final Console console;
    private final GameDataService gameDataService;

    public ConsoleManager(ConsoleOutputs consoleOutputs, GameEngine gameEngine, Console console, GameDataService gameDataService) {
        this.consoleOutputs = consoleOutputs;
        this.gameEngine = gameEngine;
        this.console = console;
        this.gameDataService = gameDataService;
    }

    private void initCommonActions() {
        console.addCommonAction("Zobraz aktuální stav", () -> {
            consoleOutputs.showCurrentStats();
            console.showChoices();
        });
        console.addCommonAction("Zobraz karty", () -> {
            consoleOutputs.showCards();
            console.showChoices();
        });
        console.addCommonAction("Začni novou hru", this::newGame);
    }

    public void startConsoleGame() {
        log.debug("startConsoleGame");
        console.executeConsoleInputLoop();
    }

    public void playGame() {
        log.debug("playGame");
        newGame();
    }

    //1
    private void newGame() {
        log.debug("newGame");
        consoleOutputs.initMessage();
        console.clearPhaseActions();
        console.clearCommonActions();
        for (Factions faction : factions) {
            console.addPhaseAction(faction.name(), () -> {
                gameEngine.getGameDataService().selectFaction(gameEngine.getFactionService().getFactionMap().get(faction));
                gameDataService.newGame();
                initCommonActions();
                lookoutPhase();
            });
        }
        console.showFactionChoices();
    }

    //2
    private void lookoutPhase() {
        log.debug("lookoutPhase");
        console.clearPhaseActions();
        console.addPhaseAction("Fáze výhledu", () -> {

        });
        console.showChoices();
    }
}
