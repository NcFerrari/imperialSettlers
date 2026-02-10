package cz.games.lp.backend.file_workers;

import cz.games.lp.backend.BackendManager;
import cz.games.lp.backend.json_object.CardJSON;
import cz.games.lp.backend.json_object.FactionJSON;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

public record JsonCreator(BackendManager backendManager) {

    public List<CardJSON> loadCardData(String filePath) {
        return loadData(filePath, CardJSON.class);
    }

    public List<FactionJSON> loadFactionData(String filePath) {
        return loadData(filePath, FactionJSON.class);
    }

    private <T> List<T> loadData(String filePath, Class<T> clazz) {
        backendManager.log(getClass()).info("loading {} file", filePath);
        InputStream stream = getClass().getClassLoader().getResourceAsStream(filePath);
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return mapper.readValue(stream, type);
    }
}
