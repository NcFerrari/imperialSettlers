package cz.games.lp.backend.engine;

import cz.games.lp.backend.engine.consolegame.ConsoleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class GameApplication {

    private final ConsoleManager console;
    private final GameServices gameServices;

    public GameApplication(ConsoleManager console, GameServices gameServices) {
        this.console = console;
        this.gameServices = gameServices;
    }

    public void startApplication() {
        log.debug("startApplication");
        CompletableFuture<String> future = gameServices.getGameDataService().prepareData();
        future.join();
        console.startConsoleGame();
        console.playGame();
    }
}
