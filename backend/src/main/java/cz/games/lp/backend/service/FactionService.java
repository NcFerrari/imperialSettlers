package cz.games.lp.backend.service;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.FactionTitles;

import java.util.Map;

public interface FactionService {

    Map<FactionTitles, FactionDTO> getFactionMap();

    FactionDTO selectFaction();
}
