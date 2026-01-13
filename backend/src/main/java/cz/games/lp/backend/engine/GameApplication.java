package cz.games.lp.backend.engine;

import cz.games.lp.backend.engine.consolegame.ConsoleListener;
import cz.games.lp.gamecore.service.SourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class GameApplication {

    private final ConsoleListener consoleListener;
    private final SourceService sourceService;

    public GameApplication(ConsoleListener consoleListener, SourceService sourceService) {
        this.consoleListener = consoleListener;
        this.sourceService = sourceService;
    }

    public void startApplication() {
        log.debug("startApplication");
        CompletableFuture<String> future = sourceService.prepareData();
        future.join();
        consoleListener.startConsoleGame();
    }
}
