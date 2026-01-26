package cz.games.lp.backend.service;

import cz.games.lp.gamecore.components.Player;

import java.util.List;
import java.util.Map;

public interface PlayerService {

    void initializePlayers(int playersCount);

    void setUpSourcesForCurrentPlayer();

    void dealFirstCards();

    void performLookoutPhase();

    void performProductionPhase();

    void nextPlayer();

    Player getCurrentPlayer();

    Player getFirstPlayer();

    List<Player> getPlayers();
}
