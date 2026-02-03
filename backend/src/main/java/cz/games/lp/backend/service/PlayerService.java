package cz.games.lp.backend.service;

import cz.games.lp.gamecore.components.Player;

import java.util.List;
import java.util.UUID;

public interface PlayerService {

    void initializePlayers(UUID uuid, int playersCount);

    Player getCurrentPlayer();

    List<Player> getPlayers();

    boolean allPlayersHaveBeenProcessed(UUID uuid);
}
