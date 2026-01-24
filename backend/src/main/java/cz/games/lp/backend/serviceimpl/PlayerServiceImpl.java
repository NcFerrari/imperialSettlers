package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.gamecore.GameManager;
import cz.games.lp.gamecore.components.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        IntStream.range(0, playersCount).forEach(i -> gameManager.addPlayer());
        gameManager.setFirstPlayer();
    }

    @Override
    public void setUpSourcesForPlayer(Player player) {
        log.debug("setSourcesForPlayer");
        player.setUpOwnSources();
    }

    @Override
    public void dealFirstCards() {
        log.debug("dealFirstCards");
        gameManager.getPlayers().forEach(Player::dealFirstCards);
    }

    @Override
    public void perfrormLookoutPhase() {
        log.debug("perfrormLookoutPhase");
        gameManager.getPlayers().forEach(Player::performLookoutPhase);
    }

    @Override
    public void performProductionPhase() {
        log.debug("performProductionPhase");
        gameManager.getPlayers().forEach(Player::performProductionPhase);
    }
}
