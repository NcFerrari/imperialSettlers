package cz.games.lp.backend.engine.consolegame;

import cz.games.lp.backend.engine.GameEngine;
import cz.games.lp.common.enums.Factions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.parser.ParserException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

@Slf4j
@Component
public class ConsoleListener {

    private final Map<String, Runnable> phaseActions = new LinkedHashMap<>();
    private final Map<String, Runnable> commonActions = new LinkedHashMap<>();
    private final Executor executor;
    private final ApplicationContext ctx;
    private final Outputs outputs;
    private final GameEngine gameEngine;
    private GameOperations gameOperation;

    public ConsoleListener(@Qualifier("consoleExecutor") Executor executor, ApplicationContext ctx, Outputs outputs, GameEngine gameEngine) {
        this.executor = executor;
        this.ctx = ctx;
        this.outputs = outputs;
        this.gameEngine = gameEngine;
        commonActions.put("Zobraz aktuální stav", outputs::showCurrentStats);
    }

    public void startConsoleGame() {
        log.debug("startConsoleGame");
        outputs.initMessage();
        executor.execute(this::cliRunner);
    }

    public void cliRunner() {
        log.debug("cliRunner");
        outputs.selectFactionMessage();
        gameOperation = GameOperations.SELECT_FACTION;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (!Thread.currentThread().isInterrupted()) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                if ("exit".equalsIgnoreCase(line.trim())) {
                    SpringApplication.exit(ctx, () -> 0);
                    return;
                }
                game(line);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
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
            outputs.wrongChoice();
            return;
        }
        number--;
        if (number > phaseActions.size() + commonActions.size() || number < 0) {
            outputs.wrongChoice();
            return;
        }
        Stream.of(phaseActions.values(), commonActions.values()).flatMap(Collection::stream).toList().get(number).run();
        outputs.showOffer(phaseActions.keySet(), commonActions.keySet());
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
                    outputs.lookoutPhaseActivated();
                });
                outputs.showOffer(phaseActions.keySet(), commonActions.keySet());
            }
            default -> {
                outputs.wrongChoice();
                outputs.selectFactionMessage();
            }
        }
    }
}
