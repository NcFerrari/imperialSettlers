package cz.games.lp.backend.orchestration;

import cz.games.lp.backend.infrastructure.mapping.GameDataMapper;
import cz.games.lp.backend.service.commonservices.GamePartsServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Orchestrator of the mapping data and game logic
 */
@Slf4j
@Component
public class GameOrchestrator {

    private final GamePartsServices gamePartsServices;
    private final GameDataMapper gameDataMapper;
    private final ConsoleOrchestrator consoleOrchestrator;

    public GameOrchestrator(GamePartsServices gamePartsServices, GameDataMapper gameDataMapper, ConsoleOrchestrator consoleOrchestrator) {
        this.gamePartsServices = gamePartsServices;
        this.gameDataMapper = gameDataMapper;
        this.consoleOrchestrator = consoleOrchestrator;
    }

    public void startGame() {
        log.debug("startGame");
        prepareGameData();
        UUID roomID = gamePartsServices.getGameService().createNewGameRoom();
        UUID playerID = gamePartsServices.getPlayerService().addPlayer(roomID);
        consoleOrchestrator.startConsoleGame(roomID, playerID);
    }

    private void prepareGameData() {
        log.debug("prepareGameData");
        prepareCardData();
        prepareFactionData();
    }

    private void prepareCardData() {
        log.debug("prepareCardData");
        CompletableFuture<String> mappingCardsFromJSONFuture = gameDataMapper.mapAllCardsData();
        mappingCardsFromJSONFuture.join();
    }

    private void prepareFactionData() {
        log.debug("prepareFactionData");
        CompletableFuture<String> mappingFactionsFromJSONFuture = gameDataMapper.mapAllFactions();
        mappingFactionsFromJSONFuture.join();
    }
}
