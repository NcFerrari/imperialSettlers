package cz.games.lp.backend.infrastructure.mapping.mappers;

import cz.games.lp.backend.infrastructure.mapping.jsonobjects.FactionJSON;
import cz.games.lp.gamecore.components.Faction;
import cz.games.lp.gamecore.components.enums.FactionTypes;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface FactionMapper {

    void mapToFactionDTO(Map<String, FactionJSON> source, @MappingTarget Map<FactionTypes, Faction> target);
}
