package cz.games.lp.backend.service;

import cz.games.lp.gamecore.GameRoom;
import cz.games.lp.gamecore.components.Player;

import java.util.List;

public interface PlayerService {

    void initializePlayers(GameRoom gameRoom, int playersCount);

    void setUpSourcesForCurrentPlayer();

    void nextPlayer();

    Player getCurrentPlayer();

    List<Player> getPlayers();

    boolean allPlayersHaveBeenProcessed();
}
