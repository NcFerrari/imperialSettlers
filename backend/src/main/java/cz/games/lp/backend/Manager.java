package cz.games.lp.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Bootstrap class for the backend application.
 */
@SpringBootApplication
public class Manager {

    public static void main(String[] args) {
        new Manager().run(args);
    }

    public void run(String[] args) {
        ApplicationContext context = SpringApplication.run(Manager.class, args);
        GameOrchestrator gameOrchestrator = context.getBean(GameOrchestrator.class);
        gameOrchestrator.initializeGame();
    }
}
