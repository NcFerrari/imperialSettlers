package cz.games.lp.backend.mapping.mappers;

import cz.games.lp.backend.mapping.jsonobjects.CardJSON;
import cz.games.lp.common.dto.CardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface CardMapper {

    void mapToCardDTO(Map<String, CardJSON> source, @MappingTarget Map<String, CardDTO> target);

}
