package cz.games.lp.backend.engine;

import cz.games.lp.gamecore.service.GameDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class GameApplication {

    private final GameEngine gameEngine;
    private final ConsoleListener consoleListener;
    private final GameDataService gameDataService;

    public GameApplication(GameEngine gameEngine, ConsoleListener consoleListener, GameDataService gameDataService) {
        this.gameEngine = gameEngine;
        this.consoleListener = consoleListener;
        this.gameDataService = gameDataService;
    }

    public void startApplication() {
        log.debug("startApplication");
        CompletableFuture<String> future = gameEngine.prepareData();
        future.join();
        consoleListener.startConsoleGame();
        log.info(gameDataService.getGameData().round());
    }
}
