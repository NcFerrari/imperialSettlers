package cz.games.lp.backend.initializer;

import cz.games.lp.backend.engine.GameEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GameInitializer {

    private final GameEngine gameEngine;

    public GameInitializer(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void prepare() {
        log.info("loading data");
    }
}
