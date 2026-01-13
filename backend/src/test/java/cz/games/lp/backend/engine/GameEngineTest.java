package cz.games.lp.backend.engine;

import cz.games.lp.backend.data.CardJSON;
import cz.games.lp.backend.data.FactionJSON;
import cz.games.lp.backend.data.JsonManager;
import cz.games.lp.backend.serviceimpl.mapping.CardMapper;
import cz.games.lp.backend.serviceimpl.mapping.FactionMapper;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameEngineTest {

    @Mock
    private JsonManager jsonManager;

    @Mock
    private CardMapper cardMapper;

    @Mock
    private FactionMapper factionMapper;

//    @InjectMocks
//    private SourceInit engine;

    private List<CardJSON> cardJSONList;
    private List<FactionJSON> factionJSONList;

    @BeforeEach
    void setup() {
        CardJSON card = new CardJSON();
        card.setCardId("c1");
        card.setCardName("Samurai Strike");
        cardJSONList = List.of(card);

        FactionJSON faction = new FactionJSON();
        faction.setFaction("JAPAN_F");
        factionJSONList = List.of(faction);
    }

//    @Test
//    void loadAllCardsDataTest() {
//        when(jsonManager.loadData(CardJSON.class, "data/cards.json")).thenReturn(cardJSONList);

//        Map<String, CardJSON> result = engine.loadAllCardsData();

//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("c1", result.get("c1").getCardId());
//    }
//
//    @Test
//    void loadAllFactionsDataTest() {
//        when(jsonManager.loadData(FactionJSON.class, "data/factions.json")).thenReturn(factionJSONList);

//        Map<String, FactionJSON> result = engine.loadAllFactionsData();

//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("JAPAN_F", result.get("JAPAN_F").getFaction());
//    }
//
//    @Test
//    void mMapAllCardsDataTest() {
//        doAnswer(invocation -> {
//            Map<String, CardJSON> raw = invocation.getArgument(0);
//            Map<String, CardDTO> target = invocation.getArgument(1);
//
//            raw.forEach((k, v) -> {
//                CardDTO dto = new CardDTO();
//                dto.setCardId(v.getCardId());
//                dto.setCardName(v.getCardName());
//                dto.setCardCategory(CardCategories.ACTION);
//                dto.setCardType(CardTypes.FACTION);
//                dto.setCardEffect(List.of(CardEffects.SWORD, CardEffects.FOOD));
//                dto.setColors(List.of(Colors.BLUE, Colors.PURPLE));
//                dto.setCondition(Conditions.RED_3);
//                dto.setDealSource(Sources.WOOD);
//                target.put(k, dto);
//            });
//            return null;
//        }).when(cardMapper).mapToCardDTO(anyMap(), anyMap());

//        engine.mapAllCardsData(cardJSONList.stream().collect(java.util.stream.Collectors.toMap(CardJSON::getCardId, c -> c)));
//
//        CardDTO dto = engine.getCardMap().get("c1");
//        assertNotNull(dto);
//        assertEquals("c1", dto.getCardId());
//        assertEquals("Samurai Strike", dto.getCardName());
//        assertEquals(CardCategories.ACTION, dto.getCardCategory());
//        assertEquals(CardTypes.FACTION, dto.getCardType());
//        assertTrue(dto.getCardEffect().contains(CardEffects.SWORD));
//        assertTrue(dto.getColors().contains(Colors.PURPLE));
//        assertEquals(Conditions.RED_3, dto.getCondition());
//        assertEquals(Sources.WOOD, dto.getDealSource());
//    }

//    @Test
//    void mapAllFactionsDataTest() {
//        doAnswer(invocation -> {
//            Map<String, FactionJSON> raw = invocation.getArgument(0);
//            Map<String, FactionDTO> target = invocation.getArgument(1);
//
//            raw.forEach((k, v) -> {
//                FactionDTO dto = new FactionDTO();
//                dto.setFaction(Factions.JAPAN_F);
//                dto.setFactionProduction(List.of(Sources.WOOD, Sources.STONE));
//                dto.setSaveSource(Sources.CARD);
//                target.put(k, dto);
//            });
//            return null;
//        }).when(factionMapper).mapToFactionDTO(anyMap(), anyMap());

//        engine.mapAllFactions(factionJSONList.stream().collect(java.util.stream.Collectors.toMap(FactionJSON::getFaction, f -> f)));
//
//        FactionDTO dto = engine.getFactionMap().get("JAPAN_F");
//        assertNotNull(dto);
//        assertEquals(Factions.JAPAN_F, dto.getFaction());
//        assertTrue(dto.getFactionProduction().contains(Sources.STONE));
//        assertEquals(Sources.CARD, dto.getSaveSource());
//    }

//    @Test
//    void prepareDataTest() {
//        when(jsonManager.loadData(CardJSON.class, "data/cards.json")).thenReturn(cardJSONList);
//        when(jsonManager.loadData(FactionJSON.class, "data/factions.json")).thenReturn(factionJSONList);
//
//        doAnswer(invocation -> {
//            invocation.getArgument(0);
//            Map<String, CardDTO> target = invocation.getArgument(1);
//            target.put("c1", new CardDTO());
//            return null;
//        }).when(cardMapper).mapToCardDTO(anyMap(), anyMap());
//
//        doAnswer(invocation -> {
//            invocation.getArgument(0);
//            Map<String, FactionDTO> target = invocation.getArgument(1);
//            target.put("JAPAN_F", new FactionDTO());
//            return null;
//        }).when(factionMapper).mapToFactionDTO(anyMap(), anyMap());

//        CompletableFuture<String> future = engine.prepareData();
//        String result = future.join();
//
//        assertEquals("loadingData", result);
//        assertTrue(engine.getCardMap().containsKey("c1"));
//        assertTrue(engine.getFactionMap().containsKey("JAPAN_F"));

//        verify(jsonManager).loadData(CardJSON.class, "data/cards.json");
//        verify(jsonManager).loadData(FactionJSON.class, "data/factions.json");
//
//        verify(cardMapper).mapToCardDTO(anyMap(), anyMap());
//        verify(factionMapper).mapToFactionDTO(anyMap(), anyMap());
//    }
}