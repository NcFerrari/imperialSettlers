package cz.games.lp.backend;

import cz.games.lp.backend.engine.ConsoleListener;
import cz.games.lp.backend.engine.GameEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.CompletableFuture;

@Slf4j
@SpringBootApplication
public class GameApplication {

    private static GameEngine gameEngine;
    private static ConsoleListener consoleListener;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GameApplication.class, args);
        gameEngine = context.getBean(GameEngine.class);
        consoleListener = context.getBean(ConsoleListener.class);
        startApplication();
    }

    private static void startApplication() {
        CompletableFuture<String> future = gameEngine.prepareData();
        future.join();
        consoleListener.start();
        System.out.println("test");
    }
}
