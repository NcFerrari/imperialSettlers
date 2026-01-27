package cz.games.lp.backend.service;

import cz.games.lp.gamecore.components.Player;

import java.util.List;

public interface PlayerService {

    void initializePlayers(int playersCount);

    void setUpSourcesForCurrentPlayer();

    void nextPlayer();

    Player getCurrentPlayer();

    Player getFirstPlayer();

    List<Player> getPlayers();

    boolean allPlayersHaveBeenProcessed();
}
