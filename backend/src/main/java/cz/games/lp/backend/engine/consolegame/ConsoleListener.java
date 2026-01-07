package cz.games.lp.backend.engine.consolegame;

import cz.games.lp.backend.engine.GameEngine;
import cz.games.lp.backend.serviceimpl.UserInputServiceImpl;
import cz.games.lp.common.enums.Factions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;

@Slf4j
@Component
public class ConsoleListener {

    private final Executor executor;
    private final ApplicationContext ctx;
    private final Outputs outputs;
    private final UserInputServiceImpl userInputService;
    private final GameEngine gameEngine;
    private GameOperations gameOperation;

    public ConsoleListener(@Qualifier("consoleExecutor") Executor executor, ApplicationContext ctx, Outputs outputs, UserInputServiceImpl userInputService, GameEngine gameEngine) {
        this.executor = executor;
        this.ctx = ctx;
        this.outputs = outputs;
        this.userInputService = userInputService;
        this.gameEngine = gameEngine;
    }

    public void startConsoleGame() {
        log.debug("startConsoleGame");
        outputs.initMessage();
        gameOperation = GameOperations.SELECT_FACTION;
        outputs.selectFactionMessage();
        executor.execute(this::cliRunner);
    }

    public void cliRunner() {
        log.debug("cliRunner");
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
            case SHOW_STATS -> outputs.showStats();
        }
    }

    private void selectFaction(String line) {
        log.debug("selectFaction");
        switch (line) {
            case "1", "2", "3", "4", "5", "6", "7", "8" -> {
                int number = Integer.parseInt(line);
                userInputService.selectFaction(gameEngine.getFactionMap().get(Factions.values()[number - 1].name()));
                gameOperation = GameOperations.SHOW_STATS;
                outputs.showStats();
            }
            default -> {
                outputs.wrongChoice();
                outputs.selectFactionMessage();
            }
        }
    }
}
