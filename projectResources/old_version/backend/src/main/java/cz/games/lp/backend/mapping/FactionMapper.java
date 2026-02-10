package cz.games.lp.backend.mapping;

import cz.games.lp.backend.json_object.FactionJSON;
import cz.games.lp.common.dto.FactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Map;

@Mapper
public interface FactionMapper {

    void mapToFactionDTO(Map<String, FactionJSON> source, @MappingTarget Map<String, FactionDTO> target);
}
