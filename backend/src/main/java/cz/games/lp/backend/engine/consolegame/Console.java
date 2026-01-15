package cz.games.lp.backend.engine.consolegame;

import cz.games.lp.backend.engine.GameEngine;
import cz.games.lp.common.enums.Factions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Component
public class Console {

    private final Map<String, Runnable> phaseActions = new LinkedHashMap<>();
    private final Map<String, Runnable> commonActions = new LinkedHashMap<>();
    private final ConsoleOutputs consoleOutputs;
    private final GameEngine gameEngine;
    private final ConsoleInput consoleInput;
    private GameOperations gameOperation;

    public Console(ConsoleOutputs outputs, GameEngine gameEngine, ConsoleInput consoleInput) {
        this.consoleOutputs = outputs;
        this.gameEngine = gameEngine;
        this.consoleInput = consoleInput;
        commonActions.put("Zobraz aktuální stav", outputs::showCurrentStats);
        commonActions.put("Začni novou hru", this::newGame);
    }

    public void startConsoleGame() {
        log.debug("startConsoleGame");
        consoleInput.start();
    }

    public void newGame() {
        log.debug("newGame");
        consoleOutputs.initMessage();
        consoleOutputs.selectFactionMessage();
        gameOperation = GameOperations.SELECT_FACTION;
    }

    private void game(String line) {
        log.debug("game");
        switch (gameOperation) {
            case SELECT_FACTION -> selectFaction(line);
            case OFFER -> offer(line);
        }
    }

    private void offer(String line) {
        log.debug("offer");
        int number;
        try {
            number = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            consoleOutputs.wrongChoice();
            return;
        }
        number--;
        if (number > phaseActions.size() + commonActions.size() || number < 0) {
            consoleOutputs.wrongChoice();
            return;
        }
        Stream.of(phaseActions.values(), commonActions.values()).flatMap(Collection::stream).toList().get(number).run();
        consoleOutputs.showOffer(phaseActions.keySet(), commonActions.keySet());
    }

    private void selectFaction(String line) {
        log.debug("selectFaction");
        switch (line) {
            case "1", "2", "3", "4", "5", "6", "7", "8" -> {
                int number = Integer.parseInt(line);
                gameEngine.getGameDataService().selectFaction(gameEngine.getSourceService().getFactionMap().get(Factions.values()[number - 1].name()));
                gameEngine.getGameDataService().newGame();
                gameOperation = GameOperations.OFFER;
                phaseActions.put("Aktivuj fázi rozhledu", () -> {
                    gameEngine.getGameDataService().proceedLookoutPhase();
                    consoleOutputs.lookoutPhaseActivated();
                });
            }
            default -> {
                consoleOutputs.wrongChoice();
                consoleOutputs.selectFactionMessage();
            }
        }
    }
}
