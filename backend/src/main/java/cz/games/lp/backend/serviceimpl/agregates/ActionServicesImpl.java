package cz.games.lp.backend.serviceimpl.agregates;

import cz.games.lp.backend.service.ProductionService;
import cz.games.lp.backend.service.agregates.ActionServices;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class ActionServicesImpl implements ActionServices {

    private final ProductionService productionService;

    public ActionServicesImpl(ProductionService productionService) {
        this.productionService = productionService;
    }
}
