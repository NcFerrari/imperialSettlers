package cz.games.lp.backend.mapping;

import cz.games.lp.backend.data.FactionJSON;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.Factions;
import cz.games.lp.common.enums.Sources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FactionMapperTest {

    private FactionMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new FactionMapperImpl();
    }

    private static class TestMapper extends FactionMapperImpl {
        List<Sources> sources(List<String> list) {
            return stringListToSourcesList(list);
        }

        FactionDTO factionDTO(FactionJSON json) {
            return factionJSONToFactionDTO(json);
        }
    }

    @Test
    void stringListToSourcesList_nullTest() {
        TestMapper tm = new TestMapper();
        assertThat(tm.sources(null)).isNull();
    }

    @Test
    void stringListToSourcesList_nonNullTest() {
        TestMapper tm = new TestMapper();
        List<Sources> result = tm.sources(List.of(Sources.STONE.name(), Sources.WOOD.name()));
        assertThat(result).containsExactly(Sources.STONE, Sources.WOOD);
    }

    @Test
    void factionJSONToFactionDTO_nullTest() {
        TestMapper tm = new TestMapper();
        assertThat(tm.factionDTO(null)).isNull();
    }

    @Test
    void factionJSONToFactionDTO_allFieldsNullTest() {
        TestMapper tm = new TestMapper();
        FactionJSON json = new FactionJSON();
        FactionDTO dto = tm.factionDTO(json);

        assertThat(dto).isNotNull();
        assertThat(dto.getFaction()).isNull();
        assertThat(dto.getFactionProduction()).isNull();
        assertThat(dto.getSaveSource()).isNull();
    }

    @Test
    void factionJSONToFactionDTO_withValuesTest() {
        TestMapper tm = new TestMapper();
        FactionJSON json = new FactionJSON();
        json.setFaction(Factions.BARBARIAN_F.name());
        json.setFactionProduction(List.of(Sources.SWORD.name(), Sources.SETTLER.name()));
        json.setSaveSource(Sources.WOOD.name());

        FactionDTO dto = tm.factionDTO(json);
        assertThat(dto.getFaction()).isEqualTo(Factions.BARBARIAN_F);
        assertThat(dto.getFactionProduction()).containsExactly(Sources.SWORD, Sources.SETTLER);
        assertThat(dto.getSaveSource()).isEqualTo(Sources.WOOD);
    }

    @Test
    void mapToFactionDTO_nullSourceTest() {
        Map<String, FactionDTO> target = new HashMap<>();
        mapper.mapToFactionDTO(null, target);
        assertThat(target).isEmpty();
    }

    @Test
    void mapToFactionDTO_withDataTest() {
        Map<String, FactionJSON> source = new HashMap<>();
        Map<String, FactionDTO> target = new HashMap<>();

        FactionJSON json = new FactionJSON();
        json.setFaction(Factions.JAPAN_M.name());
        source.put("f1", json);

        mapper.mapToFactionDTO(source, target);

        assertThat(target).hasSize(1);
        FactionDTO dto = target.get("f1");
        assertThat(dto).isNotNull();
        assertThat(dto.getFaction()).isEqualTo(Factions.JAPAN_M);
    }
}
