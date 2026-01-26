package cz.games.lp.backend.orchestration;

import cz.games.lp.backend.infrstructure.mapping.GameDataMapper;
import cz.games.lp.backend.service.agregates.GameServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Orchestrator of the mapping data and game logic
 */
@Slf4j
@Component
public class GameOrchestrator {

    private final GameServices gameServices;
    private final GameDataMapper gameDataMapper;
    private final ConsoleOrchestrator consoleOrchestrator;

    public GameOrchestrator(GameServices gameServices, GameDataMapper gameDataMapper, ConsoleOrchestrator consoleOrchestrator) {
        this.gameServices = gameServices;
        this.gameDataMapper = gameDataMapper;
        this.consoleOrchestrator = consoleOrchestrator;
    }

    public void startGame() {
        log.debug("startGame");
        prepareGameData();
        gameServices.getPlayerService().initializePlayers(1);
        consoleOrchestrator.startConsoleGame();
    }

    private void prepareGameData() {
        log.debug("prepareGameData");
        CompletableFuture<String> mappingCardsFromJSONFuture = gameDataMapper.mapAllCardsData();
        mappingCardsFromJSONFuture.join();
        CompletableFuture<String> mappingFactionsFromJSONFuture = gameDataMapper.mapAllFactions();
        mappingFactionsFromJSONFuture.join();
    }
}
