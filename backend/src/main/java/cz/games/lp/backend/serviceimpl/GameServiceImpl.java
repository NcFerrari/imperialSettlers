package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.FactionService;
import cz.games.lp.backend.service.GameService;
import cz.games.lp.gamecore.actions.GameRoomActions;
import cz.games.lp.gamecore.components.GameRoom;
import cz.games.lp.gamecore.components.enums.FactionTypes;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final GameRoomActions gameRoomActions;
    private final FactionService factionService;

    public GameServiceImpl(FactionService factionService, CardService cardService) {
        gameRoomActions = new GameRoomActions(factionService.getFactionActions(), cardService.getCardActions());
        this.factionService = factionService;
    }

    @Override
    public UUID createNewGameRoom() {
        log.debug("createNewGameRoom");
        return gameRoomActions.createNewGameRoom();
    }

    @Override
    public Set<UUID> getRooms() {
        log.debug("getRooms");
        return gameRoomActions.getRooms();
    }

    @Override
    public GameRoom getRoom(UUID roomID) {
        log.debug("getRoom");
        return gameRoomActions.getRoom(roomID);
    }

    @Override
    public List<FactionTypes> getRemainingFactions(UUID roomID) {
        log.debug("getRemainingFactions");
        return gameRoomActions.getRemainingFactions(roomID);
    }

    @Override
    public void newGame(UUID roomID) {
        log.debug("newGame");
        gameRoomActions.newGame(roomID);
    }

    @Override
    public Map<UUID, List<String>> dealFirstCardsToAllPlayers(UUID roomID) {
        log.debug("dealFirstCardsToAllPlayers");
        return gameRoomActions.dealFirstCardsToAllPlayers(roomID);
    }

    @Override
    public Map<UUID, List<String>> performLookoutPhase(UUID roomID) {
        log.debug("performLookoutPhase");
        return gameRoomActions.performLookoutPhase(roomID);
    }
}
