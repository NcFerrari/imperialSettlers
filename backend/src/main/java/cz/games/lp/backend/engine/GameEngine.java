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
    private Map<String, CardJSON> rawCardMap;
    private Map<String, FactionJSON> rawFactionMap;

    public GameEngine(JsonManager jsonCreator, CardMapper cardMapper, FactionMapper factionMapper) {
        this.jsonManager = jsonCreator;
        this.cardMapper = cardMapper;
        this.factionMapper = factionMapper;
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
        cardMapper.mapToCardDTO(rawCardMap, cardMap);
    }

    private void loadAllFactionData() {
        log.debug("loadAllFactionData");
        List<FactionJSON> list = jsonManager.loadData(FactionJSON.class, "data/factions.json");
        rawFactionMap = list.stream().collect(Collectors.toMap(FactionJSON::getFaction, Function.identity()));
    }

    private void mapAllFactions() {
        log.debug("mapAllFactions");
        factionMapper.mapToFactionDTO(rawFactionMap, factionMap);
    }
}
