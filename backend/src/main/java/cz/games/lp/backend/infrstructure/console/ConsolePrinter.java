package cz.games.lp.backend.infrstructure.console;

import cz.games.lp.gamecore.GameManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class ConsolePrinter {

    private static final String SEPARATOR = "===============================================";
    private final AtomicInteger atomicInteger = new AtomicInteger();
    private String choiceTitle = "";
    private final GameManager gameManager;

    public ConsolePrinter(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void initMessage() {
        log.debug("startConsoleGame");
        log.info("----------------------------");
        log.info("START IMPERIAL SETTLERS GAME");
        log.info("----------------------------");
    }

    public void wrongChoice() {
        log.debug("wrongChoice");
        log.info("Spatna volba!");
    }

    public void showCurrentStats() {
        log.debug("showStats");
        separator();
        log.info("Zvolena frakce: {}", gameManager.getCurrentPlayer().getFaction());
        log.info("Kolo: {}", gameManager.getRoundNumber());
        log.info("Pocet bodu: {}", gameManager.getCurrentPlayer().getVictoryPoints());
        log.info("Aktualni faze: {}", gameManager.getCurrentPhase());
        log.info("Suroviny:");
        gameManager.getCurrentPlayer().getOwnSources().forEach((source, value) -> log.info("- {}: {}", source, value));
        separator();
    }

    public void exiting() {
        log.debug("exiting");
        log.info("Game over immediately");
    }

    public void showCards() {
        log.debug("showCards");
        separator();
        log.info("Karty v ruce:");
        gameManager.getCurrentPlayer().getCardsInHand().forEach(card -> log.info(card.toString()));
        separator();
        separator();
        log.info("Postavené lokace:");
        gameManager.getCurrentPlayer().getBuiltLocations().forEach(card -> log.info(card.toString()));
        separator();
    }

    public void showInput(List<String> actions) {
        log.debug("showInput");
        separator();
        log.info("{}", choiceTitle);
        separator();
        atomicInteger.set(1);
        actions.forEach(text -> log.info("{}. {}", atomicInteger.getAndIncrement(), text));
    }

    public void setChoiceTitle(String text) {
        log.debug("setChoiceTitle {}", text);
        choiceTitle = text;
    }

    private void separator() {
        log.info(SEPARATOR);
    }
}
