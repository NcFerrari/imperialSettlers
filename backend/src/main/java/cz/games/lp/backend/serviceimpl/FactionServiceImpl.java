package cz.games.lp.backend.serviceimpl;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.Factions;
import cz.games.lp.gamecore.service.FactionService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class FactionServiceImpl implements FactionService {

    @Getter
    private final Map<Factions, FactionDTO> factionMap = new EnumMap<>(Factions.class);
}
