package cz.games.lp.backend.engine.consolegame;

import cz.games.lp.gamecore.service.GameDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class ConsoleOutputs {

    private final GameDataService gameDataService;
    private final AtomicInteger offerCounter = new AtomicInteger();

    public ConsoleOutputs(GameDataService gameDataService) {
        this.gameDataService = gameDataService;
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
        log.info("Zvolena frakce: {}", gameDataService.getGameData().getSelectedFaction());
        log.info("Kolo: {}", gameDataService.getGameData().getRound());
        log.info("Pocet bodu: {}", gameDataService.getGameData().getScorePoints());
        log.info("Aktualni faze: {}", gameDataService.getGameData().getCurrentPhase());
        log.info("Suroviny:");
        gameDataService.getGameData().getOwnSupplies().values().forEach(supply -> log.info("- {}: {}", supply.getSources(), supply.getCount()));
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

    }
}
