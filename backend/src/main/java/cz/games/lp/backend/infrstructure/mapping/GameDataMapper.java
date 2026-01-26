package cz.games.lp.backend.infrstructure.mapping;

import cz.games.lp.backend.infrstructure.mapping.mappers.CardMapper;
import cz.games.lp.backend.infrstructure.mapping.mappers.FactionMapper;
import cz.games.lp.gamecore.GameManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class GameDataMapper {

    private final GameManager gameManager;
    private final GameDataLoader gameDataLoader;
    private final CardMapper cardMapper;
    private final FactionMapper factionMapper;

    public GameDataMapper(GameManager gameManager, GameDataLoader gameDataLoader, CardMapper cardMapper, FactionMapper factionMapper) {
        this.gameManager = gameManager;
        this.gameDataLoader = gameDataLoader;
        this.cardMapper = cardMapper;
        this.factionMapper = factionMapper;
    }

    @Async("thread")
    public CompletableFuture<String> mapAllCardsData() {
        log.debug("mapAllCards");
        gameDataLoader.loadAllCardsData();
        cardMapper.mapToCardDTO(gameDataLoader.getLoadedCards(), gameManager.getCardCatalog().cardMap());
        return CompletableFuture.completedFuture("loading cards ...");
    }

    @Async("thread")
    public CompletableFuture<String> mapAllFactions() {
        log.debug("mapAllFactions");
        gameDataLoader.loadAllFactionsData();
        factionMapper.mapToFactionDTO(gameDataLoader.getLoadedFactions(), gameManager.getFactionCatalog().factionMap());
        return CompletableFuture.completedFuture("loading factions ...");
    }
}
