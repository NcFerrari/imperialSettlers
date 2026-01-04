package cz.games.lp.backend;

import cz.games.lp.backend.engine.GameApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Manager {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Manager.class, args);
        GameApplication gameApplication = context.getBean(GameApplication.class);
        gameApplication.startApplication();
    }
}
