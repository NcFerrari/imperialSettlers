package cz.games.lp.backend.infrstructure.console;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class ConsoleUI {

    private final Map<String, Runnable> actions = new LinkedHashMap<>();
    private final Map<String, Runnable> commonActions = new LinkedHashMap<>();
    private final ConsolePrinter consolePrinter;
    private final Executor executor;
    private boolean cliIsRunning;

    public ConsoleUI(ConsolePrinter consolePrinter, @Qualifier("consoleExecutor") Executor executor) {
        this.consolePrinter = consolePrinter;
        this.executor = executor;
    }

    public void executeConsoleInputLoop() {
        log.debug("executeConsoleInputLoop");
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
                if ("exit".equalsIgnoreCase(line.trim())) {
                    cliIsRunning = false;
                    consolePrinter.exiting();
                    break;
                }
                int number = validateChoice(line);
                if (number > -1) {
                    Stream.of(actions.values(), commonActions.values()).flatMap(Collection::stream).toList().get(number).run();
                }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        } finally {
            cliIsRunning = false;
        }
    }

    private int validateChoice(String line) {
        log.debug("validateChoice");
        int number;
        try {
            number = Integer.parseInt(line);
            number--;
            if (number >= actions.size() + commonActions.size() || number < 0) {
                throw new NumberFormatException();
            }
            return number;
        } catch (NumberFormatException e) {
            consolePrinter.wrongChoice();
            showActionChoices();
            return -1;
        }
    }

    public void addCommonAction(String key, Runnable runnable) {
        log.debug("addCommonAction");
        commonActions.put(key, runnable);
    }

    public void clearCommonActions() {
        log.debug("clearCommonActions");
        commonActions.clear();
    }

    public void addActions(Map<String, Runnable> mapOfActions) {
        log.debug("addPhaseAction");
        actions.clear();
        actions.putAll(mapOfActions);
    }

    public void showActionChoices(String text) {
        log.debug("showActionChoices {}", text);
        consolePrinter.setChoiceTitle(text);
        showActionChoices();
    }

    public void showActionChoices() {
        log.debug("showActionChoices");
        consolePrinter.showInput(Stream.concat(actions.keySet().stream(), commonActions.keySet().stream()).toList());
    }
}
