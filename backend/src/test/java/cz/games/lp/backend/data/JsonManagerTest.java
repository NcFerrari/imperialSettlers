package cz.games.lp.backend.data;

import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonManagerTest {

    private JsonManager jsonManager;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        jsonManager = new JsonManager(objectMapper);
    }

    @Test
    void loadDataTest() {
        List<Dummy> result = jsonManager.loadData(Dummy.class, "test-data.json");

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
    }

    record Dummy(String name) {

        public String getName() {
            return name;
        }
    }
}
