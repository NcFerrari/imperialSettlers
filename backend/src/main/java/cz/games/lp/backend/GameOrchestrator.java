package cz.games.lp.backend;

import cz.games.lp.backend.mapping.GameDataMapper;
import cz.games.lp.backend.service.GameService;
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
        initializePlayers();
        initializeGame();
        dealFirstFourCardsToPlayers();
    }

    private void dealFirstFourCardsToPlayers() {
        log.debug("dealFirstFourCardsToPlayers");
        gameService.getPlayerService().getPlayers().forEach(player -> {
            gameService.getCardService().dealFactionCardToPlayer(player);
            gameService.getCardService().dealFactionCardToPlayer(player);
            gameService.getCardService().dealCommonCardToPlayer(player);
            gameService.getCardService().dealCommonCardToPlayer(player);
        });
    }

    private void initializeGame() {
        log.debug("initializeGame");
        gameService.getGameSessionService().newGame();
    }

    private void initializePlayers() {
        log.debug("initializePlayers");
        gameService.getPlayerService().addPlayers();
    }

    private void prepareGameData() {
        log.debug("prepareGameData");
        CompletableFuture<String> cardsFuture = gameDataMapper.mapAllCardsData();
        cardsFuture.join();
        CompletableFuture<String> factionsFuture = gameDataMapper.mapAllFactions();
        factionsFuture.join();
    }
}
