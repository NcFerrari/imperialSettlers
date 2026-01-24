package cz.games.lp.backend.service;

import cz.games.lp.gamecore.components.Player;

public interface PlayerService {

    void initializePlayers(int playersCount);

    void setUpSourcesForPlayer(Player player);

    void dealFirstCards();

    void perfrormLookoutPhase();

    void performProductionPhase();
}
