package cz.games.lp.backend.mapping;

import cz.games.lp.backend.json_object.CardJSON;
import cz.games.lp.common.dto.CardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Map;

@Mapper
public interface CardMapper {

    void mapToCardDTO(Map<String, CardJSON> source, @MappingTarget Map<String, CardDTO> target);
}
