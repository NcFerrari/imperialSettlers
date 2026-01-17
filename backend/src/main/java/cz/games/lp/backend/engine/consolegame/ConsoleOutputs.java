package cz.games.lp.backend.engine.consolegame;

import cz.games.lp.backend.engine.GameServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class ConsoleOutputs {

    private final GameServices gameServices;
    private final AtomicInteger offerCounter = new AtomicInteger();

    public ConsoleOutputs(GameServices gameServices) {
        this.gameServices = gameServices;
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
        log.info("===============================================");
        log.info("Zvolena frakce: {}", gameServices.getGameData().getSelectedFaction());
        log.info("Kolo: {}", gameServices.getGameData().getRound());
        log.info("Pocet bodu: {}", gameServices.getGameData().getScorePoints());
        log.info("Aktualni faze: {}", gameServices.getGameData().getCurrentPhase());
        log.info("Suroviny:");
        gameServices.getGameData().getOwnSupplies().values().forEach(supply -> log.info("- {}: {}", supply.getSources(), supply.getCount()));
        log.info("===============================================");
    }

    public void showStartOffer(Set<String> offerActions) {
        log.debug("showStartOffer");
        showSpecificOffer("Vyberte si frakci:", offerActions);
    }

    public void showOffer(Set<String> offerActions, Set<String> commonActions) {
        log.debug("showOffer");
        showSpecificOffer("Zvolte akci:", offerActions);
        commonActions.forEach(commonAction -> log.info("{}. {}", offerCounter.getAndIncrement(), commonAction));
    }

    private void showSpecificOffer(String text, Set<String> offerActions) {
        log.debug("showSpecificOffer");
        log.info(text);
        offerCounter.set(1);
        offerActions.forEach(offerAction -> log.info("{}. {}", offerCounter.getAndIncrement(), offerAction));
    }

    public void showCards() {
        log.debug("showCards");
        log.info("===============================================");
        log.info("Karty v ruce:");
        gameServices.getGameData().getCardsInHand().forEach(card -> log.info(card.toString()));
        log.info("Postavené lokace:");
        gameServices.getGameData().getBuiltLocations().forEach(card -> log.info(card.toString()));
        log.info("===============================================");
    }
}
