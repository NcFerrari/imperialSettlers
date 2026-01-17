package cz.games.lp.backend;

import cz.games.lp.backend.mapping.GameDataMapper;
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

    public GameOrchestrator(GameDataMapper gameDataMapper) {
        this.gameDataMapper = gameDataMapper;
    }

    public void initializeGame() {
        log.debug("initializeGame");
        prepareGameData();
    }

    private void prepareGameData() {
        log.debug("prepareGameData");
        CompletableFuture<String> cardsFuture = gameDataMapper.mapAllCardsData();
        cardsFuture.join();
        CompletableFuture<String> factionsFuture = gameDataMapper.mapAllFactions();
        factionsFuture.join();
    }
}
