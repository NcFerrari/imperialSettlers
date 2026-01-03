package cz.games.lp.backend.engine;

import cz.games.lp.backend.data.CardJSON;
import cz.games.lp.backend.data.FactionJSON;
import cz.games.lp.backend.data.JsonCreator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GameEngine {

    private final JsonCreator jsonCreator;
    @Getter
    private Map<String, CardJSON> rawCardMap;
    @Getter
    private Map<String, FactionJSON> rawFactionMap;

    public GameEngine(JsonCreator jsonCreator) {
        this.jsonCreator = jsonCreator;
    }

    @Async("thread")
    public void prepareData() {
        log.info("prepareData");
        loadAllCardData();
        loadAllFactionData();
    }

    public void loadAllCardData() {
        log.info("loadAllCardData");
        List<CardJSON> list = jsonCreator.loadData(CardJSON.class, "data/cards.json");
        rawCardMap = list.stream().collect(Collectors.toMap(CardJSON::getCardId, Function.identity()));
    }

    public void loadAllFactionData() {
        log.info("loadAllFactionData");
        List<FactionJSON> list = jsonCreator.loadData(FactionJSON.class, "data/factions.json");
        rawFactionMap = list.stream().collect(Collectors.toMap(FactionJSON::getFaction, Function.identity()));
    }
}
