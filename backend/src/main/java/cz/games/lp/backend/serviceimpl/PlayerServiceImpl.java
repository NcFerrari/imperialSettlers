package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.gamecore.GameManager;
import cz.games.lp.gamecore.components.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
public class PlayerServiceImpl implements PlayerService {

    private final GameManager gameManager;

    public PlayerServiceImpl(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void initializePlayers(int playersCount) {
        log.debug("initializePlayers");
        IntStream.range(0, Math.min(4, playersCount)).forEach(i -> gameManager.addPlayer());
        gameManager.setFirstPlayer();
    }

    @Override
    public void setUpSourcesForCurrentPlayer() {
        log.debug("setSourcesForPlayer");
        getCurrentPlayer().setUpOwnSources();
    }

    @Override
    public void nextPlayer() {
        log.debug("nextPlayer");
        gameManager.nextPlayer();
    }

    @Override
    public Player getCurrentPlayer() {
        return gameManager.getCurrentPlayer();
    }

    @Override
    public Player getFirstPlayer() {
        return gameManager.getFirstPlayer();
    }

    @Override
    public List<Player> getPlayers() {
        return gameManager.getPlayers();
    }

    @Override
    public boolean allPlayersHaveBeenProcessed() {
        return getCurrentPlayer().equals(getFirstPlayer());
    }
}
