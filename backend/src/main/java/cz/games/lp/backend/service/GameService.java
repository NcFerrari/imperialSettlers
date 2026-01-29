package cz.games.lp.backend.service;

import cz.games.lp.common.enums.ProductionStatus;

public interface GameService {

    void newGame();

    void performLookoutPhase();

    ProductionStatus performProductionPhase();

    int getFactionCardDeckCount();
}
