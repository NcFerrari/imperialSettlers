package cz.games.lp.backend.infrastructure.console;

import cz.games.lp.gamecore.actions.ProduceReport;
import cz.games.lp.gamecore.components.GameRoom;
import cz.games.lp.gamecore.components.enums.Sources;
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

    public void initMessage() {
        log.debug("startConsoleGame");
        log.info("----------------------------");
        log.info("HRAJEME HRU OSADNICI IMPERIA");
        log.info("----------------------------");
    }

    public void wrongChoice() {
        log.info("Spatna volba!");
    }

    public void showCurrentStats(GameRoom gameRoom) {
        log.debug("showStats");
        separator();
        log.info("Zvolena frakce: {}", gameRoom.getCurrentPlayer().getFaction().getFactionType());
        log.info("Kolo: {}", gameRoom.getRoundNumber());
        log.info("Pocet bodu: {}", gameRoom.getCurrentPlayer().getVictoryPoints());
        log.info("Aktualni faze: {}", gameRoom.getCurrentPhase());
        log.info("Suroviny:");
        gameRoom.getCurrentPlayer().getOwnSources().forEach((source, value) -> log.info("- {}: {}", source, value));
        separator();
    }

    public void exiting() {
        log.info("Game over immediately");
    }

    public void showCards(GameRoom gameRoom) {
        log.debug("showCards");
        separator();
        log.info("Karty v ruce:");
        gameRoom.getCurrentPlayer().getCardsInHand().forEach(card -> log.info(card.toString()));
        separator();
        separator();
        log.info("Dohody:");
        gameRoom.getCurrentPlayer().getDeals().forEach(deal -> log.info(deal.toString()));
        separator();
        separator();
        log.info("Postavené lokace:");
        gameRoom.getCurrentPlayer().getBuiltLocations().values().forEach(card -> log.info(card.toString()));
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

    public void dealProduceInfo(ProduceReport produceReport) {
        log.info("karta {} produkuje z dohody: {}", produceReport.getCardID(), produceReport.getDeal());
    }

    public void factionBoardProduction(Sources source) {
        log.info("Frakční deska produkuje {}", source);
    }

    public void cardProduction(ProduceReport produceReport) {
        log.info("Karta {} produkuje: {}", produceReport.getCardID(), produceReport.getSource());
    }
}
