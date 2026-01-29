package cz.games.lp.backend.service;

import cz.games.lp.common.enums.ProductionStatus;

public interface ProductionService {

    ProductionStatus performProductionPhase();
}
