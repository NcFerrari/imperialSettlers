package cz.games.lp.backend.engine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;

@Slf4j
@Component
public class ConsoleListener {

    private final Executor executor;
    private final ApplicationContext ctx;

    public ConsoleListener(@Qualifier("consoleExecutor") Executor executor, ApplicationContext ctx) {
        this.executor = executor;
        this.ctx = ctx;
    }

    private void cliRunner() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (!Thread.currentThread().isInterrupted()) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                if ("exit".equalsIgnoreCase(line.trim())) {
                    SpringApplication.exit(ctx, () -> 0);
                    return;
                }
                gameInput(line);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    public void start() {
        log.info("----------------------------");
        log.info("START IMPERIAL SETTLERS GAME");
        log.info("----------------------------");
        executor.execute(this::cliRunner);
    }

    private void gameInput(String line) {
        log.info("you typed: {}", line);
    }
}
