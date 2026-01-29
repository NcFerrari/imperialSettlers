package cz.games.lp.backend.infrstructure.mapping;

import cz.games.lp.backend.service.agregates.MappingServices;
import cz.games.lp.gamecore.GameManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class GameDataMapper {

    private final GameManager gameManager;
    private final MappingServices mappingServices;

    public GameDataMapper(GameManager gameManager, MappingServices mappingServices) {
        this.gameManager = gameManager;
        this.mappingServices = mappingServices;
    }

    @Async("thread")
    public CompletableFuture<String> mapAllCardsData() {
        log.debug("mapAllCards");
        mappingServices.getGameDataLoader().loadAllCardsData();
        mappingServices.getCardMapper().mapToCardDTO(mappingServices.getGameDataLoader().getLoadedCards(), gameManager.getCardActions().getCardCatalog().cardMap());
        return CompletableFuture.completedFuture("loading cards ...");
    }

    @Async("thread")
    public CompletableFuture<String> mapAllFactions() {
        log.debug("mapAllFactions");
        mappingServices.getGameDataLoader().loadAllFactionsData();
        mappingServices.getFactionMapper().mapToFactionDTO(mappingServices.getGameDataLoader().getLoadedFactions(), gameManager.getFactionActions().getFactionCatalog().factionMap());
        return CompletableFuture.completedFuture("loading factions ...");
    }
}
