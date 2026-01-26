package cz.games.lp.backend.service;

import cz.games.lp.common.enums.FactionTypes;
import cz.games.lp.gamecore.components.Player;

import java.util.List;

public interface FactionService {

    List<FactionTypes> getRemainingFactions();

    void removeSelectedFaction(FactionTypes faction);

    void selectFaction(Player player, FactionTypes faction);

    void resetFactionSelection();
}
