package cz.games.lp.backend.serviceimpl;

import cz.games.lp.gamecore.GameManager;
import cz.games.lp.gamecore.service.GameDataService;
import cz.games.lp.gamecore.service.GameManagerService;
import org.springframework.stereotype.Service;

@Service
public class GameManagerServiceImpl implements GameManagerService {

    private final GameManager gameManager;
    private final GameDataService gameDataService;

    public GameManagerServiceImpl(GameDataService gameDataService) {
        this.gameDataService = gameDataService;
        gameManager = new GameManager();
    }

    @Override
    public String newGame() {
        gameManager.newGame(gameDataService.getGameData());
        return "new game";
    }
}
