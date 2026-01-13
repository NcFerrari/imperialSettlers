package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.data.CardJSON;
import cz.games.lp.backend.data.FactionJSON;
import cz.games.lp.backend.data.JsonManager;
import cz.games.lp.backend.serviceimpl.mapping.CardMapper;
import cz.games.lp.backend.serviceimpl.mapping.FactionMapper;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.gamecore.service.SourceService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SourceServiceImpl implements SourceService {

    @Getter
    private final Map<String, CardDTO> cardMap = new HashMap<>();
    @Getter
    private final Map<String, FactionDTO> factionMap = new HashMap<>();
    private final JsonManager jsonManager;
    private final CardMapper cardMapper;
    private final FactionMapper factionMapper;

    public SourceServiceImpl(JsonManager jsonCreator, CardMapper cardMapper, FactionMapper factionMapper) {
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

    private void mapAllCardsData(Map<String, CardJSON> jsonCardsDataMap) {
        log.debug("mapAllCards");
        cardMapper.mapToCardDTO(jsonCardsDataMap, cardMap);
    }

    private Map<String, CardJSON> loadAllCardsData() {
        log.debug("loadAllCardData");
        List<CardJSON> list = jsonManager.loadData(CardJSON.class, "data/cards.json");
        return list.stream().collect(Collectors.toMap(CardJSON::getCardId, Function.identity()));
    }

    private void mapAllFactions(Map<String, FactionJSON> jsonFactionsDataMap) {
        log.debug("mapAllFactions");
        factionMapper.mapToFactionDTO(jsonFactionsDataMap, factionMap);
    }

    private Map<String, FactionJSON> loadAllFactionsData() {
        log.debug("loadAllFactionData");
        List<FactionJSON> list = jsonManager.loadData(FactionJSON.class, "data/factions.json");
        return list.stream().collect(Collectors.toMap(FactionJSON::getFaction, Function.identity()));
    }
}
