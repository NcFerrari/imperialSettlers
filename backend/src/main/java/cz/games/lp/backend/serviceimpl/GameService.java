package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.engine.GameEngine;
import cz.games.lp.gamecore.service.GameDataService;
import cz.games.lp.gamecore.service.GameManagerService;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
public class GameService {

    private final GameEngine gameEngine;
    private final GameDataService gameDataService;
    private final GameManagerService gameManagerService;

    public GameService(GameEngine gameEngine, GameDataService gameDataService, GameManagerService gameManagerService) {
        this.gameEngine = gameEngine;
        this.gameDataService = gameDataService;
        this.gameManagerService = gameManagerService;
    }
}
