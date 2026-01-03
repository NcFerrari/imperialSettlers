package cz.games.lp.backend.engine;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;

@Component
public class ConsoleListener implements CommandLineRunner {

    private final Executor executor;
    private final ApplicationContext ctx;

    public ConsoleListener(@Qualifier("consoleExecutor") Executor executor, ApplicationContext ctx) {
        this.executor = executor;
        this.ctx = ctx;
    }

    @Override
    public void run(String... args) {
        executor.execute(this::cliRunner);
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
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }


}
