package cz.games.lp.backend.service;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.FactionTitles;

import java.util.List;
import java.util.Map;

public interface FactionService {

    Map<FactionTitles, FactionDTO> getFactionMap();

    List<FactionTitles> getRemainingFactions();

    void removeSelectedFaction(FactionTitles faction);
}
