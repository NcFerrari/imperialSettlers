package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.GameService;
import cz.games.lp.backend.service.ProductionService;
import cz.games.lp.gamecore.components.GameRoom;
import cz.games.lp.gamecore.actions.FactionActions;
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
    private final FactionActions factionActions;

    public GameServiceImpl(CardActions cardActions, ProductionService productionService, Map<UUID, GameRoom> gameRooms, FactionActions factionActions) {
        this.cardActions = cardActions;
        this.productionService = productionService;
        this.gameRooms = gameRooms;
        this.factionActions = factionActions;
    }

    @Override
    public UUID createNewGameRoom() {
        log.debug("createNewGameRoom");
        GameRoom gameRoom = new GameRoom();
        gameRooms.put(gameRoom.getId(), gameRoom);
        return gameRoom.getId();
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
    public Map<UUID, GameRoom> getGameRooms() {
        return gameRooms;
    }

    @Override
    public GameRoom getGameRoom(UUID roomUUID) {
        return gameRooms.get(roomUUID);
    }

    @Override
    public void actionsWhenChooseFaction(UUID uuid, FactionTypes faction) {
        getGameRoom(uuid).actionsWhenChooseFaction(factionActions.getFactionCatalog().factionMap().get(faction), cardActions);
    }
}
