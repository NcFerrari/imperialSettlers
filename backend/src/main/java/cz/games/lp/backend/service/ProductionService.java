package cz.games.lp.backend.service;

import cz.games.lp.gamecore.components.enums.ProductionStatus;

public interface ProductionService {

    ProductionStatus performProductionPhase();
}
