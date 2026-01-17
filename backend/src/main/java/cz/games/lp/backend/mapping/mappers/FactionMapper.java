package cz.games.lp.backend.mapping.mappers;

import cz.games.lp.backend.mapping.jsonobjects.FactionJSON;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.Factions;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface FactionMapper {

    void mapToFactionDTO(Map<String, FactionJSON> source, @MappingTarget Map<Factions, FactionDTO> target);
}
