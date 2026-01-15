package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.data.CardJSON;
import cz.games.lp.backend.data.FactionJSON;
import cz.games.lp.backend.data.JsonManager;
import cz.games.lp.backend.serviceimpl.mapping.CardMapper;
import cz.games.lp.backend.serviceimpl.mapping.FactionMapper;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.gamecore.GameData;
import cz.games.lp.gamecore.service.CardService;
import cz.games.lp.gamecore.service.FactionService;
import cz.games.lp.gamecore.service.GameDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GameDataServiceImpl implements GameDataService {

    private final JsonManager jsonManager;
    private final CardMapper cardMapper;
    private final FactionMapper factionMapper;
    private final FactionService factionService;
    private final CardService cardService;
    private final GameData gameData;

    public GameDataServiceImpl(JsonManager jsonManager, CardMapper cardMapper, FactionMapper factionMapper, FactionService factionService, CardService cardService) {
        this.jsonManager = jsonManager;
        this.cardMapper = cardMapper;
        this.factionMapper = factionMapper;
        this.factionService = factionService;
        this.cardService = cardService;
        gameData = new GameData();
    }

    @Override
    public GameData getGameData() {
        return gameData;
    }

    @Override
    public void selectFaction(FactionDTO faction) {
        gameData.setSelectedFaction(faction);
    }

    @Override
    public void newGame() {
        gameData.newGame();
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
        cardMapper.mapToCardDTO(jsonCardsDataMap, cardService.getCardMap());
    }

    private Map<String, CardJSON> loadAllCardsData() {
        log.debug("loadAllCardData");
        List<CardJSON> list = jsonManager.loadData(CardJSON.class, "data/cards.json");
        return list.stream().collect(Collectors.toMap(CardJSON::getCardId, Function.identity()));
    }

    private void mapAllFactions(Map<String, FactionJSON> jsonFactionsDataMap) {
        log.debug("mapAllFactions");
        factionMapper.mapToFactionDTO(jsonFactionsDataMap, factionService.getFactionMap());
    }

    private Map<String, FactionJSON> loadAllFactionsData() {
        log.debug("loadAllFactionData");
        List<FactionJSON> list = jsonManager.loadData(FactionJSON.class, "data/factions.json");
        return list.stream().collect(Collectors.toMap(FactionJSON::getFaction, Function.identity()));
    }

    @Override
    public void proceedLookoutPhase() {

    }
}
