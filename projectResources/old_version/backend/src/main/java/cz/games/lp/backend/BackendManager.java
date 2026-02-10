package cz.games.lp.backend;

import cz.games.lp.backend.json_object.CardJSON;
import cz.games.lp.backend.file_workers.JsonCreator;
import cz.games.lp.backend.json_object.FactionJSON;
import cz.games.lp.backend.mapping.CardMapper;
import cz.games.lp.backend.mapping.FactionMapper;
import cz.games.lp.backend.service.LoggerService;
import cz.games.lp.backend.service_impl.LoggerServiceImpl;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import lombok.Getter;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BackendManager {

    private final LoggerService logger = LoggerServiceImpl.getInstance();
    private final CardMapper cardMapper = Mappers.getMapper(CardMapper.class);
    private final FactionMapper factionMapper = Mappers.getMapper(FactionMapper.class);
    @Getter
    private final Map<String, CardDTO> cards = new HashMap<>();
    @Getter
    private final Map<String, FactionDTO> factions = new HashMap<>();
    private Map<String, CardJSON> rawCardMap;
    private Map<String, FactionJSON> rawFactionMap;

    public void prepareCardAndFactionData() {
        log(getClass()).info("load card data");
        Thread loadingCards = new Thread(this::loadAllCardData);
        loadingCards.start();
        try {
            loadingCards.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log(getClass()).info("mapping cards to DTO");
        Thread mappingCard = new Thread(() -> cardMapper.mapToCardDTO(rawCardMap, cards));
        mappingCard.start();
        try {
            mappingCard.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log(getClass()).info("load faction data");
        Thread loadingFactions = new Thread(this::loadAllFactionData);
        loadingFactions.start();
        try {
            loadingFactions.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log(getClass()).info("mapping fields to DTO");
        Thread mappingFields = new Thread(() -> factionMapper.mapToFactionDTO(rawFactionMap, factions));
        mappingFields.start();
        try {
            mappingFields.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void loadAllCardData() {
        log(getClass()).info("loadAllCardData");
        long timeStart = System.currentTimeMillis();
        log(getClass()).info("starting reading card data from json");
        List<CardJSON> jsonData = new JsonCreator(this).loadCardData("data/cards.json");
        rawCardMap = jsonData.stream().collect(Collectors.toMap(CardJSON::getCardId, Function.identity()));
        log(getClass()).info("Data loaded in {} ms", System.currentTimeMillis() - timeStart);
    }

    private void loadAllFactionData() {
        log(getClass()).info("loadAllFactionData");
        long timeStart = System.currentTimeMillis();
        log(getClass()).info("starting reading factions data from json");
        List<FactionJSON> jsonData = new JsonCreator(this).loadFactionData("data/factions.json");
        rawFactionMap = jsonData.stream().collect(Collectors.toMap(FactionJSON::getFaction, Function.identity()));
        log(getClass()).info("Data loaded in {} ms", System.currentTimeMillis() - timeStart);
    }

    public <T> Logger log(Class<T> clazz) {
        return logger.log(clazz);
    }
}
