package cz.games.lp.backend.service.agregates;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.FactionTitles;

import java.util.Map;

public interface GameCoreService {

    Map<String, CardDTO> getCardMap();

    Map<FactionTitles, FactionDTO> getFactionMap();
}
