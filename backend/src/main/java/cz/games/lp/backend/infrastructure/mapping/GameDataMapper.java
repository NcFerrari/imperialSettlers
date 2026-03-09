package cz.games.lp.backend.infrastructure.mapping;

import cz.games.lp.backend.service.commonservices.GamePartsServices;
import cz.games.lp.backend.service.commonservices.MappingServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class GameDataMapper {

    private final MappingServices mappingServices;
    private final GamePartsServices gamePartsServices;

    public GameDataMapper(MappingServices mappingServices, GamePartsServices gamePartsServices) {
        this.mappingServices = mappingServices;
        this.gamePartsServices = gamePartsServices;
    }

    @Async("thread")
    public CompletableFuture<String> mapAllCardsData() {
        log.debug("mapAllCards");
        mappingServices.getGameDataLoader().loadAllCardsData();
        mappingServices.getCardMapper().mapToCardDTO(mappingServices.getGameDataLoader().getLoadedCards(), gamePartsServices.getCardService().cardMap());
        return CompletableFuture.completedFuture("loading cards ...");
    }

    @Async("thread")
    public CompletableFuture<String> mapAllFactions() {
        log.debug("mapAllFactions");
        mappingServices.getGameDataLoader().loadAllFactionsData();
        mappingServices.getFactionMapper().mapToFactionDTO(mappingServices.getGameDataLoader().getLoadedFactions(), gamePartsServices.getFactionService().factionMap());
        return CompletableFuture.completedFuture("loading factions ...");
    }
}
