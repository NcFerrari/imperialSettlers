package cz.games.lp.backend.service;

public interface GameService {

    CardService getCardService();

    FactionService getFactionService();

    GameSessionService getGameSessionService();

    PhaseService getPhaseService();

    PlayerService getPlayerService();
}
