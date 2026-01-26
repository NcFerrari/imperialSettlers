package cz.games.lp.backend.service.agregates;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.FactionService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.backend.service.ProductionService;

public interface GameServices {

    CardService getCardService();

    FactionService getFactionService();

    PlayerService getPlayerService();

    ProductionService getProductionService();
}