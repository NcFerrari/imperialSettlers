package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.ProductionService;
import cz.games.lp.gamecore.actions.ProductionActions;
import org.springframework.stereotype.Service;

@Service
public class ProductionServiceImpl implements ProductionService {

    private final ProductionActions productionActions;

    public ProductionServiceImpl(ProductionActions productionActions) {
        this.productionActions = productionActions;
    }

    @Override
    public void performProductionPhase() {
        productionActions.performProductionPhase();
    }
}