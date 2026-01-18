package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.FactionService;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.FactionTitles;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Slf4j
@Service
public class FactionServiceImpl implements FactionService {

    @Getter
    private final Map<FactionTitles, FactionDTO> factionMap = new EnumMap<>(FactionTitles.class);

    @Override
    public FactionDTO selectFaction() {
        return factionMap.get(FactionTitles.BARBARIAN_F);
    }
}
