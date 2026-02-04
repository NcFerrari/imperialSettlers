package cz.games.lp.backend.service;

import cz.games.lp.gamecore.actions.FactionActions;
import cz.games.lp.gamecore.components.Faction;
import cz.games.lp.gamecore.components.enums.FactionTypes;

import java.util.List;
import java.util.Map;

public interface FactionService {

    Map<FactionTypes, Faction> factionMap();

    FactionActions getFactionActions();

    void resetFactionSelection(List<FactionTypes> remainingFactions);

    void removeFromChoice(List<FactionTypes> remainingFactions, FactionTypes factionType);
}
