package cz.games.lp.backend.engine;

import cz.games.lp.backend.engine.consolegame.Console;
import cz.games.lp.gamecore.service.SourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class GameApplication {

    private final Console console;
    private final SourceService sourceService;

    public GameApplication(Console console, SourceService sourceService) {
        this.console = console;
        this.sourceService = sourceService;
    }

    public void startApplication() {
        log.debug("startApplication");
        CompletableFuture<String> future = sourceService.prepareData();
        future.join();
        console.startConsoleGame();
    }
}
