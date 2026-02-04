package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.GameService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.gamecore.components.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Service
public class PlayerServiceImpl implements PlayerService {

    private final GameService gameService;

    public PlayerServiceImpl(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void initializePlayers(UUID uuid, int playersCount) {
        log.debug("initializePlayers");
        if (!gameService.getGameRoom(uuid).getPlayers().isEmpty()) {
            return;
        }
//        IntStream.range(0, Math.min(4, playersCount)).forEach(i -> gameService.getGameRoom(uuid).addPlayer());
//        gameService.getGameRoom(uuid).setFirstPlayer();
    }

    @Override
    public Player getCurrentPlayer() {
//        return gameRoom.getCurrentPlayer();
        return null;
    }

    @Override
    public List<Player> getPlayers() {
//        return gameRoom.getPlayers();
        return null;
    }

    @Override
    public boolean allPlayersHaveBeenProcessed(UUID uuid) {
//        return gameService.getGameRoom(uuid).allPlayersHaveBeenProcessed();
        return false;
    }
}
