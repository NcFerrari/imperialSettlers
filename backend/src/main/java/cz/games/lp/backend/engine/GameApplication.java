package cz.games.lp.backend.engine;

import cz.games.lp.backend.engine.consolegame.ConsoleManager;
import cz.games.lp.gamecore.service.GameDataService;
import cz.games.lp.gamecore.service.SourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class GameApplication {

    private final ConsoleManager console;
    private final GameDataService gameDataService;

    public GameApplication(ConsoleManager console, GameDataService gameDataService) {
        this.console = console;
        this.gameDataService = gameDataService;
    }

    public void startApplication() {
        log.debug("startApplication");
        CompletableFuture<String> future = gameDataService.prepareData();
        future.join();
        console.startConsoleGame();
        console.playGame();
    }
}
