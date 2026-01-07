package cz.games.lp.backend.engine;

import cz.games.lp.backend.engine.consolegame.ConsoleListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class GameApplication {

    private final GameEngine gameEngine;
    private final ConsoleListener consoleListener;

    public GameApplication(GameEngine gameEngine, ConsoleListener consoleListener) {
        this.gameEngine = gameEngine;
        this.consoleListener = consoleListener;
    }

    public void startApplication() {
        log.debug("startApplication");
        CompletableFuture<String> future = gameEngine.prepareData();
        future.join();
        consoleListener.startConsoleGame();
    }
}
