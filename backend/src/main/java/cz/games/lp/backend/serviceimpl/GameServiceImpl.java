package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.GameService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.gamecore.CardManager;
import cz.games.lp.gamecore.GameManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final GameManager gameManager;
    private final PlayerService playerService;
    private final CardManager cardManager;

    public GameServiceImpl(GameManager gameManager, PlayerService playerService, CardManager cardManager) {
        this.gameManager = gameManager;
        this.playerService = playerService;
        this.cardManager = cardManager;
    }

    @Override
    public void newGame() {
        log.debug("newGame");
        gameManager.newGame();
    }

    @Override
    public void performLookoutPhase() {
        log.debug("performLookoutPhase");
        cardManager.performLookoutPhase();
    }

    @Override
    public void performProductionPhase() {
        log.debug("performProductionPhase");
//        playerService.getPlayers().forEach(Player::performProductionPhase);
    }
}
