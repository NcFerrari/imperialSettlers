package cz.games.lp.backend.facade;

import cz.games.lp.backend.engine.GameEngine;
import org.springframework.stereotype.Service;

@Service
public class GameFacade {

    private final GameEngine gameEngine;

    public GameFacade(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }
}
