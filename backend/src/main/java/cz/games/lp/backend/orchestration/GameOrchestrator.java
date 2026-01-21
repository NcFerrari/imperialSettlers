package cz.games.lp.backend.orchestration;

import cz.games.lp.backend.infrstructure.mapping.GameDataMapper;
import cz.games.lp.backend.service.agregates.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Orchestrator of the mapping data and game logic
 */
@Slf4j
@Component
public class GameOrchestrator {

    private final GameDataMapper gameDataMapper;
    private final GameService gameService;

    public GameOrchestrator(GameDataMapper gameDataMapper, GameService gameService) {
        this.gameDataMapper = gameDataMapper;
        this.gameService = gameService;
    }

    public void startGame() {
        log.debug("startGame");
        prepareGameData();
        gameService.getPlayerService().initializePlayers();
    }

    private void prepareGameData() {
        log.debug("prepareGameData");
        CompletableFuture<String> mappingCardsFromJSONFuture = gameDataMapper.mapAllCardsData();
        mappingCardsFromJSONFuture.join();
        CompletableFuture<String> mappingFactionsFromJSONFuture = gameDataMapper.mapAllFactions();
        mappingFactionsFromJSONFuture.join();
    }
}
