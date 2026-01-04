package cz.games.lp.backend.mapping;

import cz.games.lp.backend.data.CardJSON;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.enums.CardCategories;
import cz.games.lp.common.enums.CardEffects;
import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.common.enums.Colors;
import cz.games.lp.common.enums.Conditions;
import cz.games.lp.common.enums.Sources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CardMapperTest {

    private CardMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CardMapperImpl();
    }

    @Test
    void mapToCardDTOTest() {
        Map<String, CardJSON> source = new HashMap<>();
        Map<String, CardDTO> target = new HashMap<>();

        CardJSON json = new CardJSON();
        json.setCardId("card1");
        json.setCardName("Test Card");
        json.setCardCategory(CardCategories.ACTION.name());
        json.setCardType(CardTypes.COMMON.name());
        json.setCardEffect(List.of(CardEffects.FOOD.name()));
        json.setColors(List.of(Colors.RED.name()));
        json.setCondition(Conditions.GOLD_3.name());
        json.setDealSource(Sources.SETTLER.name());
        source.put("c1", json);

        mapper.mapToCardDTO(source, target);

        assertThat(target).hasSize(1);
        CardDTO dto = target.get("c1");
        assertThat(dto).isNotNull();
        assertThat(dto.getCardId()).isEqualTo("card1");
        assertThat(dto.getCardName()).isEqualTo("Test Card");
        assertThat(dto.getCardCategory()).isEqualTo(CardCategories.ACTION);
        assertThat(dto.getCardType()).isEqualTo(CardTypes.COMMON);
        assertThat(dto.getCardEffect()).containsExactly(CardEffects.FOOD);
        assertThat(dto.getColors()).containsExactly(Colors.RED);
        assertThat(dto.getCondition()).isEqualTo(Conditions.GOLD_3);
        assertThat(dto.getDealSource()).isEqualTo(Sources.SETTLER);
    }

    @Test
    void mapNullSourceTest() {
        Map<String, CardDTO> target = new HashMap<>();
        mapper.mapToCardDTO(null, target);
        assertThat(target).isEmpty();
    }

    @Test
    void stringListToCardEffectsList_nullTest() {
        TestMapper tm = new TestMapper();
        assertThat(tm.effects()).isNull();
    }

    @Test
    void stringListToColorsList_nullTest() {
        TestMapper tm = new TestMapper();
        assertThat(tm.colors()).isNull();
    }

    @Test
    void stringListToSourcesList_nullTest() {
        TestMapper tm = new TestMapper();
        assertThat(tm.sources()).isNull();
    }

    @Test
    void cardJSONToCardDTO_nullInputTest() {
        TestMapper tm = new TestMapper();
        assertThat(tm.cardDTO(null)).isNull();
    }

    @Test
    void cardJSONToCardDTO_allFieldsNullTest() {
        TestMapper tm = new TestMapper();
        CardJSON json = new CardJSON();
        CardDTO dto = tm.cardDTO(json);

        assertThat(dto).isNotNull();
        assertThat(dto.getCardCategory()).isNull();
        assertThat(dto.getCardEffectForPosition()).isNull();
        assertThat(dto.getCardId()).isNull();
        assertThat(dto.getCardName()).isNull();
        assertThat(dto.getCardType()).isNull();
        assertThat(dto.getCondition()).isNull();
        assertThat(dto.getDealSource()).isNull();
    }

    private static class TestMapper extends CardMapperImpl {
        List<Sources> sources(List<String> list) {
            return stringListToSourcesList(list);
        }

        List<CardEffects> effects() {
            return stringListToCardEffectsList(null);
        }

        List<Colors> colors() {
            return stringListToColorsList(null);
        }

        List<Sources> sources() {
            return stringListToSourcesList(null);
        }

        CardDTO cardDTO(CardJSON json) {
            return cardJSONToCardDTO(json);
        }
    }

    @Test
    void stringListToSourcesList_nonNullTest() {
        TestMapper tm = new TestMapper();
        List<Sources> result = tm.sources(List.of(Sources.CARD.name(), Sources.LOCATION.name()));
        assertThat(result).containsExactly(Sources.CARD, Sources.LOCATION);
    }

    @Test
    void cardJSONToCardDTO_cardEffectForPositionTest() {
        TestMapper tm = new TestMapper();
        CardJSON json = new CardJSON();
        json.setCardEffectForPosition(CardEffects.FOOD.name());

        CardDTO dto = tm.cardDTO(json);
        assertThat(dto).isNotNull();
        assertThat(dto.getCardEffectForPosition()).isEqualTo(CardEffects.FOOD);
    }
}
