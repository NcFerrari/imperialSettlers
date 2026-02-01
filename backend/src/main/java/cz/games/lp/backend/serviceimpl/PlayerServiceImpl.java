package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.gamecore.GameRoom;
import cz.games.lp.gamecore.components.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
public class PlayerServiceImpl implements PlayerService {

    @Override
    public void initializePlayers(GameRoom gameRoom, int playersCount) {
        log.debug("initializePlayers");
        IntStream.range(0, Math.min(4, playersCount)).forEach(i -> gameRoom.addPlayer());
        gameRoom.setFirstPlayer();
    }

    @Override
    public void setUpSourcesForCurrentPlayer() {
        log.debug("setSourcesForPlayer");
        getCurrentPlayer().setUpOwnSources();
    }

    @Override
    public void nextPlayer() {
        log.debug("nextPlayer");
//        gameRoom.nextPlayer();
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
    public boolean allPlayersHaveBeenProcessed() {
//        return gameRoom.allPlayersHaveBeenProcessed();
        return false;
    }
}
