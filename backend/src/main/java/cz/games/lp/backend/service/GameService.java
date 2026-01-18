package cz.games.lp.backend.service;

public interface GameService {

    CardService getCardService();

    FactionService getFactionService();

    GameSessionService getGameSessionService();

    PlayerService getPlayerService();
}
