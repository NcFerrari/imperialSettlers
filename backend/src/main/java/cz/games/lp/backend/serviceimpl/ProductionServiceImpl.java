package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.GameService;
import cz.games.lp.backend.service.ProductionService;
import cz.games.lp.gamecore.actions.ProductionActions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ProductionServiceImpl implements ProductionService {

    private final ProductionActions productionActions;

    public ProductionServiceImpl(GameService gameService) {
        productionActions = new ProductionActions(gameService.getGameRoomActions());
    }

    @Override
    public void performProductionPhase(UUID roomID) {
        log.debug("performProductionPhase");
        productionActions.performProductionPhase(roomID);
    }
}
