package cz.games.lp.backend.engine.consolegame;

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
public class ConsoleInput {

    private final ApplicationContext ctx;
    private final Executor executor;

    public ConsoleInput(ApplicationContext ctx, @Qualifier("consoleExecutor") Executor executor) {
        this.ctx = ctx;
        this.executor = executor;
    }

    public void start() {
        log.debug("start");
        executor.execute(this::cliRunner);
    }

    public void cliRunner() {
        log.debug("cliRunner");
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
//                game(line);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}
