package cz.games.lp.backend.infrstructure.mapping;

import cz.games.lp.backend.service.agregates.MappingServices;
import cz.games.lp.gamecore.actions.CardActions;
import cz.games.lp.gamecore.actions.FactionActions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class GameDataMapper {

    private final MappingServices mappingServices;
    private final CardActions cardActions;
    private final FactionActions factionActions;

    public GameDataMapper(MappingServices mappingServices, CardActions cardActions, FactionActions factionActions) {
        this.mappingServices = mappingServices;
        this.cardActions = cardActions;
        this.factionActions = factionActions;
    }

    @Async("thread")
    public CompletableFuture<String> mapAllCardsData() {
        log.debug("mapAllCards");
        mappingServices.getGameDataLoader().loadAllCardsData();
        mappingServices.getCardMapper().mapToCardDTO(mappingServices.getGameDataLoader().getLoadedCards(), cardActions.getCardCatalog().cardMap());
        return CompletableFuture.completedFuture("loading cards ...");
    }

    @Async("thread")
    public CompletableFuture<String> mapAllFactions() {
        log.debug("mapAllFactions");
        mappingServices.getGameDataLoader().loadAllFactionsData();
        mappingServices.getFactionMapper().mapToFactionDTO(mappingServices.getGameDataLoader().getLoadedFactions(), factionActions.getFactionCatalog().factionMap());
        return CompletableFuture.completedFuture("loading factions ...");
    }
}
