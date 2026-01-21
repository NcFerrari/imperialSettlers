package cz.games.lp.backend.service.agregates;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.FactionService;
import cz.games.lp.backend.service.GameSessionService;
import cz.games.lp.backend.service.PlayerService;

public interface GameService {

    CardService getCardService();

    FactionService getFactionService();

    GameSessionService getGameSessionService();

    PlayerService getPlayerService();
}