package cz.games.lp.backend.service;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.FactionTitles;
import cz.games.lp.gamecore.components.Player;

import java.util.List;
import java.util.Map;

public interface FactionService {

    Map<FactionTitles, FactionDTO> getFactionMap();

    List<FactionTitles> getRemainingFactions();

    void removeSelectedFaction(FactionTitles faction);

    void selectFaction(Player player, FactionTitles faction);

    void resetFactionSelection();
}
