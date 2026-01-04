package cz.games.lp.backend.engine;

import cz.games.lp.backend.data.CardJSON;
import cz.games.lp.backend.data.FactionJSON;
import cz.games.lp.backend.data.JsonManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GameEngine {

    private final JsonManager jsonManager;
    @Getter
    private Map<String, CardJSON> rawCardMap;
    @Getter
    private Map<String, FactionJSON> rawFactionMap;

    public GameEngine(JsonManager jsonCreator) {
        this.jsonManager = jsonCreator;
    }

    @Async("thread")
    public CompletableFuture<String> prepareData() {
        log.debug("prepareData");
        loadAllCardData();
        mapAllCards();
        loadAllFactionData();
        mapAllFactions();
        return CompletableFuture.completedFuture("loadingData");
    }

    private void loadAllCardData() {
        log.debug("loadAllCardData");
        List<CardJSON> list = jsonManager.loadData(CardJSON.class, "data/cards.json");
        rawCardMap = list.stream().collect(Collectors.toMap(CardJSON::getCardId, Function.identity()));
    }

    private void mapAllCards() {
        log.debug("mapAllCards");

    }

    private void loadAllFactionData() {
        log.debug("loadAllFactionData");
        List<FactionJSON> list = jsonManager.loadData(FactionJSON.class, "data/factions.json");
        rawFactionMap = list.stream().collect(Collectors.toMap(FactionJSON::getFaction, Function.identity()));
    }

    private void mapAllFactions() {
        log.debug("mapAllFactions");
    }
}
