package cz.games.lp.backend.mapping;

import cz.games.lp.backend.mapping.jsonobjects.CardJSON;
import cz.games.lp.backend.mapping.jsonobjects.FactionJSON;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GameDataLoader {

    @Getter
    private Map<String, CardJSON> loadedCards = new HashMap<>();
    @Getter
    private Map<String, FactionJSON> loadedFactions = new HashMap<>();
    private final ObjectMapper mapper;

    public GameDataLoader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void loadAllCardsData() {
        log.debug("loadAllCardData");
        List<CardJSON> list = loadData(CardJSON.class, "data/cards.json");
        loadedCards = list.stream().collect(Collectors.toMap(CardJSON::getCardId, Function.identity()));
    }

    public void loadAllFactionsData() {
        log.debug("loadAllFactionData");
        List<FactionJSON> list = loadData(FactionJSON.class, "data/factions.json");
        loadedFactions = list.stream().collect(Collectors.toMap(FactionJSON::getFactionTitle, Function.identity()));
    }

    private <T> List<T> loadData(Class<T> clazz, String filePath) {
        log.debug("loadData");
        log.info("loading {} file", filePath);
        long timeStart = System.currentTimeMillis();
        InputStream stream = getClass().getClassLoader().getResourceAsStream(filePath);
        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        log.info("Data loaded in {} ms", System.currentTimeMillis() - timeStart);
        return mapper.readValue(stream, type);
    }
}
