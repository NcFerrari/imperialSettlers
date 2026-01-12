package cz.games.lp.backend.engine.consolegame;

import cz.games.lp.backend.serviceimpl.GameService;
import cz.games.lp.common.enums.Factions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.parser.ParserException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;

@Slf4j
@Component
public class ConsoleListener {

    private final Map<String, Runnable> offerActions = new LinkedHashMap<>();
    private final Executor executor;
    private final ApplicationContext ctx;
    private final Outputs outputs;
    private final GameService gameService;
    private GameOperations gameOperation;

    public ConsoleListener(@Qualifier("consoleExecutor") Executor executor, ApplicationContext ctx, Outputs outputs, GameService gameService) {
        this.executor = executor;
        this.ctx = ctx;
        this.outputs = outputs;
        this.gameService = gameService;
    }

    public void startConsoleGame() {
        log.debug("startConsoleGame");
        outputs.initMessage();
        gameOperation = GameOperations.SELECT_FACTION;
        outputs.selectFactionMessage();
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
                game(line);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    private void game(String line) {
        log.debug("game");
        switch (gameOperation) {
            case SELECT_FACTION -> selectFaction(line);
            case OFFER -> offer(line);
        }
    }

    private void offer(String line) {
        log.debug("offer");
        int number;
        try {
            number = Integer.parseInt(line);
        } catch (ParserException e) {
            outputs.wrongChoice();
            return;
        }
        if (number > offerActions.size() || number < 0) {
            outputs.wrongChoice();
            return;
        }

    }

    private void selectFaction(String line) {
        log.debug("selectFaction");
        switch (line) {
            case "1", "2", "3", "4", "5", "6", "7", "8" -> {
                int number = Integer.parseInt(line);
                gameService.getGameDataService().selectFaction(gameService.getGameEngine().getFactionMap().get(Factions.values()[number - 1].name()));
                gameService.getGameManagerService().newGame();
                gameOperation = GameOperations.OFFER;
                offerActions.put("Aktivuj fázi rozhledu", () -> gameService.getGameManagerService().proceedLookoutPhase());
                outputs.showOffer(offerActions.keySet());
            }
            default -> {
                outputs.wrongChoice();
                outputs.selectFactionMessage();
            }
        }
    }
}
