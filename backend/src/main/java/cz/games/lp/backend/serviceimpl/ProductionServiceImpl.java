package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.GameService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.backend.service.ProductionService;
import cz.games.lp.gamecore.actions.ProduceResult;
import cz.games.lp.gamecore.actions.ProductionActions;
import cz.games.lp.gamecore.components.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class ProductionServiceImpl implements ProductionService {

    private final ProductionActions productionActions;

    public ProductionServiceImpl(GameService gameService, PlayerService playerService, CardService cardService) {
        productionActions = new ProductionActions(gameService.getGameRoomActions(), playerService.getPlayerActions(), cardService.getCardActions());
    }

    @Override
    public Map<UUID, List<ProduceResult>> performProductionPhase(UUID roomID) {
        log.debug("performProductionPhase");
        return productionActions.performProductionPhase(roomID);
    }
}
