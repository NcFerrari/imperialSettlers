package cz.games.lp.backend.mapping;

import cz.games.lp.backend.mapping.mappers.CardMapper;
import cz.games.lp.backend.mapping.mappers.FactionMapper;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.Factions;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class GameDataMapper {

    @Getter
    private final Map<String, CardDTO> cardMap = new HashMap<>();
    @Getter
    private final Map<Factions, FactionDTO> factionMap = new EnumMap<>(Factions.class);

    private final CardMapper cardMapper;
    private final FactionMapper factionMapper;
    private final GameDataLoader gameDataLoader;

    public GameDataMapper(CardMapper cardMapper, FactionMapper factionMapper, GameDataLoader gameDataLoader) {
        this.cardMapper = cardMapper;
        this.factionMapper = factionMapper;
        this.gameDataLoader = gameDataLoader;
    }

    @Async("thread")
    public CompletableFuture<String> mapAllCardsData() {
        log.debug("mapAllCards");
        gameDataLoader.loadAllCardsData();
        cardMapper.mapToCardDTO(gameDataLoader.getLoadedCards(), getCardMap());
        return CompletableFuture.completedFuture("loading cards ...");
    }

    @Async("thread")
    public CompletableFuture<String> mapAllFactions() {
        log.debug("mapAllFactions");
        gameDataLoader.loadAllFactionsData();
        factionMapper.mapToFactionDTO(gameDataLoader.getLoadedFactions(), getFactionMap());
        return CompletableFuture.completedFuture("loading factions ...");
    }
}
