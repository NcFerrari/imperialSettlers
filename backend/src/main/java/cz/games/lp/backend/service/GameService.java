package cz.games.lp.backend.service;

import cz.games.lp.gamecore.GameRoom;
import cz.games.lp.gamecore.components.enums.ProductionStatus;

import java.util.Map;
import java.util.UUID;

public interface GameService {

    UUID createNewGameRoom(int countOfPlayers);

    void performLookoutPhase();

    ProductionStatus performProductionPhase();

    int getFactionCardDeckCount();

    Map<UUID, GameRoom> getGameRooms();

    GameRoom getGameRoom(UUID roomUUID);
}
