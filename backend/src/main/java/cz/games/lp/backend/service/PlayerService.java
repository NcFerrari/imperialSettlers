package cz.games.lp.backend.service;

import cz.games.lp.gamecore.actions.PlayerActions;
import cz.games.lp.gamecore.components.Player;
import cz.games.lp.gamecore.components.enums.FactionTypes;

import java.util.List;
import java.util.UUID;

public interface PlayerService {

    PlayerActions getPlayerActions();

    List<UUID> addPlayers(UUID roomID, int playerCount);

    List<Player> getPlayers(UUID roomID);

    Player getPlayer(UUID roomID, UUID playerID);

    void initPlayerAndUpdateGameRoom(UUID roomID, UUID playerID, FactionTypes factionType);

    boolean allPlayersHaveBeenProcessed(UUID roomID);

    void newGameForAllPlayers(UUID roomID);

    void resetAllPlayersForSelectingFaction(UUID roomID);

    void resetPlayerForSelectingFaction(UUID roomID, UUID playerID);
}
