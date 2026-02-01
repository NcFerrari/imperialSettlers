package cz.games.lp.backend.service;

import cz.games.lp.gamecore.components.enums.FactionTypes;

import java.util.List;

public interface FactionService {

    List<FactionTypes> getRemainingFactions();

    void resetFactionSelection();

    void selectFactionForCurrentPlayer(FactionTypes faction);
}
