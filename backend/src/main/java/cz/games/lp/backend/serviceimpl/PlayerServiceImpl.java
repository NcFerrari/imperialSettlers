package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.GameService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.gamecore.actions.PlayerActions;
import cz.games.lp.gamecore.components.Player;
import cz.games.lp.gamecore.components.enums.FactionTypes;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Getter
@Slf4j
@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerActions playerActions;

    public PlayerServiceImpl(GameService gameService) {
        playerActions = new PlayerActions(gameService.getGameRoomActions());
    }

    @Override
    public List<UUID> addPlayers(UUID roomID, int playerCount) {
        log.debug("addPlayers");
        return playerActions.addPlayers(roomID, playerCount);
    }

    @Override
    public List<Player> getPlayers(UUID roomID) {
        log.debug("getPlayers");
        return playerActions.getPlayers(roomID);
    }

    @Override
    public Player getPlayer(UUID roomID, UUID playerID) {
        log.debug("getPlayer");
        return playerActions.getPlayer(roomID, playerID);
    }

    @Override
    public void initPlayerAndUpdateGameRoom(UUID roomID, UUID playerID, FactionTypes factionType) {
        log.debug("initPlayerAndUpdateGameService");
        playerActions.initPlayerAndUpdateGameService(roomID, playerID, factionType);
    }

    @Override
    public boolean allPlayersHaveBeenProcessed(UUID roomID) {
        log.debug("allPlayersHaveBeenProcessed");
        return playerActions.allPlayersHaveBeenProcessed(roomID);
    }

    @Override
    public void newGameForAllPlayers(UUID roomID) {
        log.debug("newGameForAllPlayers");
        playerActions.newGameForPlayers(roomID);
    }
}
