package cz.games.lp.backend.mapping;

import cz.games.lp.backend.mapping.mappers.CardMapper;
import cz.games.lp.backend.mapping.mappers.FactionMapper;
import cz.games.lp.backend.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class GameDataMapper {

    private final CardMapper cardMapper;
    private final FactionMapper factionMapper;
    private final GameDataLoader gameDataLoader;
    private final GameService gameService;

    public GameDataMapper(CardMapper cardMapper, FactionMapper factionMapper, GameDataLoader gameDataLoader, GameService gameService) {
        this.cardMapper = cardMapper;
        this.factionMapper = factionMapper;
        this.gameDataLoader = gameDataLoader;
        this.gameService = gameService;
    }

    @Async("thread")
    public CompletableFuture<String> mapAllCardsData() {
        log.debug("mapAllCards");
        gameDataLoader.loadAllCardsData();
        cardMapper.mapToCardDTO(gameDataLoader.getLoadedCards(), gameService.getCardService().getCardMap());
        return CompletableFuture.completedFuture("loading cards ...");
    }

    @Async("thread")
    public CompletableFuture<String> mapAllFactions() {
        log.debug("mapAllFactions");
        gameDataLoader.loadAllFactionsData();
        factionMapper.mapToFactionDTO(gameDataLoader.getLoadedFactions(), gameService.getFactionService().getFactionMap());
        return CompletableFuture.completedFuture("loading factions ...");
    }
}
