package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.GameService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.backend.service.ProductionService;
import cz.games.lp.backend.service.SourceService;
import cz.games.lp.gamecore.actions.ProduceChoice;
import cz.games.lp.gamecore.actions.ProductionActions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class ProductionServiceImpl implements ProductionService {

    private final ProductionActions productionActions;

    public ProductionServiceImpl(GameService gameService, PlayerService playerService, CardService cardService, SourceService sourceService) {
        productionActions = new ProductionActions(gameService.getGameRoomActions(), playerService.getPlayerActions(), cardService.getCardActions(), sourceService.getSourceActions());
    }

    @Override
    public Map<UUID, List<ProduceChoice>> produceFactionCards(UUID roomID) {
        log.debug("performProductionPhase");
        return productionActions.produceFactionCards(roomID);
    }

    @Override
    public Map<UUID, List<ProduceChoice>> produceDeals(UUID roomID) {
        log.debug("produceDeals");
        return productionActions.produceDeals(roomID);
    }

    @Override
    public Map<UUID, ProduceChoice> produceFactionBoard(UUID roomID) {
        log.debug("produceFactionBoard");
        return productionActions.produceFactionBoard(roomID);
    }
}
