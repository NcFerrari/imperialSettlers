package cz.games.lp.backend.service;

import cz.games.lp.gamecore.actions.GameRoomActions;
import cz.games.lp.gamecore.components.GameRoom;
import cz.games.lp.gamecore.components.enums.FactionTypes;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface GameService {

    GameRoomActions getGameRoomActions();

    UUID createNewGameRoom();

    Set<UUID> getRooms();

    GameRoom getRoom(UUID roomID);

    List<FactionTypes> getRemainingFactions(UUID roomID);

    void newGame(UUID roomID);

    void dealFirstCardsToAllPlayers(UUID roomID);
}
