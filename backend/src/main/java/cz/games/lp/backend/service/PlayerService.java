package cz.games.lp.backend.service;

import cz.games.lp.gamecore.Player;

import java.util.List;

public interface PlayerService {

    void initializePlayers();

    List<Player> getPlayers();
}
