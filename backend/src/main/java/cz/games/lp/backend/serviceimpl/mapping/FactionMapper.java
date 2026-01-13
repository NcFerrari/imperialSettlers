package cz.games.lp.backend.serviceimpl.mapping;

import cz.games.lp.backend.data.FactionJSON;
import cz.games.lp.common.dto.FactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface FactionMapper {

    void mapToFactionDTO(Map<String, FactionJSON> source, @MappingTarget Map<String, FactionDTO> target);
}
