package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.GameService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.backend.service.ProductionService;
import cz.games.lp.gamecore.GameRoom;
import cz.games.lp.gamecore.components.enums.FactionTypes;
import cz.games.lp.gamecore.components.enums.ProductionStatus;
import cz.games.lp.gamecore.actions.CardActions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final CardActions cardActions;
    private final ProductionService productionService;
    private final Map<UUID, GameRoom> gameRooms;

    public GameServiceImpl(CardActions cardActions, ProductionService productionService, Map<UUID, GameRoom> gameRooms) {
        this.cardActions = cardActions;
        this.productionService = productionService;
        this.gameRooms = gameRooms;
    }

    @Override
    public UUID createNewGameRoom() {
        log.debug("createNewGameRoom");
        GameRoom gameRoom = new GameRoom();
        gameRooms.put(gameRoom.getId(), gameRoom);
        return gameRoom.getId();
    }

    public void newGame() {
//        cardActions.createNewCardDeck();
    }

    @Override
    public void performLookoutPhase() {
        log.debug("performLookoutPhase");
        cardActions.performLookoutPhase();
    }

    @Override
    public ProductionStatus performProductionPhase() {
        log.debug("performProductionPhase");
        return productionService.performProductionPhase();
    }

    @Override
    public int getFactionCardDeckCount() {
        log.debug("getFactionCardDeckCount");
//        return gameRoom.getFactionCardDeckCount();
        return 0;
    }

    @Override
    public Map<UUID, GameRoom> getGameRooms() {
        return gameRooms;
    }

    @Override
    public GameRoom getGameRoom(UUID roomUUID) {
        return gameRooms.get(roomUUID);
    }

    @Override
    public void actionsWhenChooseFaction(FactionTypes faction) {

    }
}
