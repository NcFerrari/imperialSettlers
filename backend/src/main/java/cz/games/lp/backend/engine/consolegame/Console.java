package cz.games.lp.backend.engine.consolegame;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

@Slf4j
@Component
public class Console {

    private final Map<String, Runnable> phaseActions = new LinkedHashMap<>();
    private final Map<String, Runnable> commonActions = new LinkedHashMap<>();
    private final ApplicationContext ctx;
    private final ConsoleOutputs consoleOutputs;
    private final Executor executor;
    private boolean cliIsRunning;

    public Console(ApplicationContext ctx, ConsoleOutputs consoleOutputs, @Qualifier("consoleExecutor") Executor executor) {
        this.ctx = ctx;
        this.consoleOutputs = consoleOutputs;
        this.executor = executor;
    }

    public void executeConsoleInputLoop() {
        log.debug("start");
        cliIsRunning = true;
        executor.execute(this::cliRunner);
    }

    private void cliRunner() {
        log.debug("cliRunner");
        if (!cliIsRunning) {
            throw new IllegalArgumentException("CLI is not running. Call start() method first.");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (cliIsRunning) {
                String line = reader.readLine();
                if (line == null) {
                    continue;
                }
                if ("exit".equalsIgnoreCase(line.trim())) {
                    cliIsRunning = false;
                    SpringApplication.exit(ctx, () -> 0);
                    break;
                }
                int number;
                try {
                    number = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    consoleOutputs.wrongChoice();
                    continue;
                }
                number--;
                if (number >= phaseActions.size() + commonActions.size() || number < 0) {
                    consoleOutputs.wrongChoice();
                    continue;
                }
                Stream.of(phaseActions.values(), commonActions.values()).flatMap(Collection::stream).toList().get(number).run();
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        } finally {
            cliIsRunning = false;
        }
    }

    public void addCommonAction(String key, Runnable runnable) {
        log.debug("addCommonAction");
        commonActions.put(key, runnable);
    }

    public void addPhaseAction(String key, Runnable runnable) {
        log.debug("addPhaseAction");
        phaseActions.put(key, runnable);
    }

    public void showFactionChoices() {
        log.debug("showFactionChoices");
        consoleOutputs.showStartOffer(phaseActions.keySet());
    }

    public void showChoices() {
        log.debug("showChoices");
        consoleOutputs.showOffer(phaseActions.keySet(), commonActions.keySet());
    }

    public void clearPhaseActions() {
        phaseActions.clear();
    }

    public void clearCommonActions() {
        commonActions.clear();
    }
}
