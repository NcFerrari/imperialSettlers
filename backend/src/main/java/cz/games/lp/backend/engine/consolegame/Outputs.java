package cz.games.lp.backend.engine.consolegame;

import cz.games.lp.backend.serviceimpl.GameDataServiceImpl;
import cz.games.lp.common.enums.Factions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Slf4j
@Component
public class Outputs {

    private final GameDataServiceImpl gameDataService;

    public Outputs(GameDataServiceImpl gameDataService) {
        this.gameDataService = gameDataService;
    }

    public void initMessage() {
        log.debug("startConsoleGame");
        log.info("----------------------------");
        log.info("START IMPERIAL SETTLERS GAME");
        log.info("----------------------------");
    }

    public void selectFactionMessage() {
        log.debug("selectFactionMessage");
        log.info("Vyberte si frakci:");
        IntStream.range(0, Factions.values().length).forEach(i -> log.info("{}. {}", i + 1, Factions.values()[i].name()));
    }

    public void wrongChoice() {
        log.debug("wrongChoice");
        log.info("Spatna volba!");
    }

    public void showStats() {
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
}
