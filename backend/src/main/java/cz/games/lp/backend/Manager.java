package cz.games.lp.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Bootstrap class for the backend application.
 */
@Slf4j
@SpringBootApplication
public class Manager {

    public static void main(String[] args) {
        log.debug("main");
        new Manager().run(args);
    }

    public void run(String[] args) {
        log.debug("run");
        ApplicationContext context = SpringApplication.run(Manager.class, args);
        GameOrchestrator gameOrchestrator = context.getBean(GameOrchestrator.class);
        gameOrchestrator.initializeGame();
    }
}
