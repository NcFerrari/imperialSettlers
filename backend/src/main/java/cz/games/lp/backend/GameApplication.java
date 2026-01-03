package cz.games.lp.backend;

import cz.games.lp.backend.engine.GameEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class GameApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GameApplication.class, args);
        GameEngine gameEngine = context.getBean(GameEngine.class);
        gameEngine.prepareData();
    }
}
