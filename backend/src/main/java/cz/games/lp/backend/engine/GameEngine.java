package cz.games.lp.backend.engine;

import cz.games.lp.backend.data.CardJSON;
import cz.games.lp.backend.data.FactionJSON;
import cz.games.lp.backend.data.JsonManager;
import cz.games.lp.backend.mapping.CardMapper;
import cz.games.lp.backend.mapping.FactionMapper;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GameEngine {

    @Getter
    private final Map<String, CardDTO> cardMap = new HashMap<>();
    @Getter
    private final Map<String, FactionDTO> factionMap = new HashMap<>();
    private final JsonManager jsonManager;
    private final CardMapper cardMapper;
    private final FactionMapper factionMapper;

    public GameEngine(JsonManager jsonCreator, CardMapper cardMapper, FactionMapper factionMapper) {
        this.jsonManager = jsonCreator;
        this.cardMapper = cardMapper;
        this.factionMapper = factionMapper;
    }

    @Async("thread")
    public CompletableFuture<String> prepareData() {
        log.debug("prepareData");
        mapAllCardsData(loadAllCardsData());
        mapAllFactions(loadAllFactionsData());
        return CompletableFuture.completedFuture("loadingData");
    }

    public void mapAllCardsData(Map<String, CardJSON> jsonCardsDataMap) {
        log.debug("mapAllCards");
        cardMapper.mapToCardDTO(jsonCardsDataMap, cardMap);
    }

    public Map<String, CardJSON> loadAllCardsData() {
        log.debug("loadAllCardData");
        List<CardJSON> list = jsonManager.loadData(CardJSON.class, "data/cards.json");
        return list.stream().collect(Collectors.toMap(CardJSON::getCardId, Function.identity()));
    }

    public void mapAllFactions(Map<String, FactionJSON> jsonFactionsDataMap) {
        log.debug("mapAllFactions");
        factionMapper.mapToFactionDTO(jsonFactionsDataMap, factionMap);
    }

    public Map<String, FactionJSON> loadAllFactionsData() {
        log.debug("loadAllFactionData");
        List<FactionJSON> list = jsonManager.loadData(FactionJSON.class, "data/factions.json");
        return list.stream().collect(Collectors.toMap(FactionJSON::getFaction, Function.identity()));
    }
}