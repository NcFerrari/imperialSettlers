package cz.games.lp.backend.infrstructure.mapping;

import cz.games.lp.backend.service.agregates.GameCoreService;
import cz.games.lp.backend.service.agregates.MappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class GameDataMapper {

    private final MappingService mappingService;
    private final GameCoreService gameCoreService;

    public GameDataMapper(MappingService mappingService, GameCoreService gameCoreService) {
        this.mappingService = mappingService;
        this.gameCoreService = gameCoreService;
    }

    @Async("thread")
    public CompletableFuture<String> mapAllCardsData() {
        log.debug("mapAllCards");
        mappingService.getGameDataLoader().loadAllCardsData();
        mappingService.getCardMapper().mapToCardDTO(mappingService.getGameDataLoader().getLoadedCards(), gameCoreService.getCardMap());
        return CompletableFuture.completedFuture("loading cards ...");
    }

    @Async("thread")
    public CompletableFuture<String> mapAllFactions() {
        log.debug("mapAllFactions");
        mappingService.getGameDataLoader().loadAllFactionsData();
        mappingService.getFactionMapper().mapToFactionDTO(mappingService.getGameDataLoader().getLoadedFactions(), gameCoreService.getFactionMap());
        return CompletableFuture.completedFuture("loading factions ...");
    }
}
