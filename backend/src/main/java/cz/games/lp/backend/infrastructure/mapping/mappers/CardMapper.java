package cz.games.lp.backend.infrastructure.mapping.mappers;

import cz.games.lp.backend.infrastructure.mapping.jsonobjects.CardJSON;
import cz.games.lp.gamecore.components.Card;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface CardMapper {

    void mapToCardDTO(Map<String, CardJSON> source, @MappingTarget Map<String, Card> target);

}
