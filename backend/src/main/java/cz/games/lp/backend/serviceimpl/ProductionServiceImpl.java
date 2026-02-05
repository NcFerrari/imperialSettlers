package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.ProductionService;
import cz.games.lp.gamecore.actions.ProductionActions;
import cz.games.lp.gamecore.components.enums.ProductionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductionServiceImpl implements ProductionService {

    private final ProductionActions productionActions;

    public ProductionServiceImpl() {
        productionActions = new ProductionActions();
    }

    @Override
    public ProductionStatus performProductionPhase() {
        log.debug("performProductionPhase");
        return productionActions.performProductionPhase();
    }
}
