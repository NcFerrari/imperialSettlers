package cz.games.lp.backend.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
public class JsonCreator {

    private final ObjectMapper mapper;

    public JsonCreator(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> List<T> loadData(Class<T> clazz, String filePath) {
        log.info("loadData");
        log.info("loading {} file", filePath);
        long timeStart = System.currentTimeMillis();
        InputStream stream = getClass().getClassLoader().getResourceAsStream(filePath);
        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        log.info("Data loaded in {} ms", System.currentTimeMillis() - timeStart);
        return mapper.readValue(stream, type);
    }
}
