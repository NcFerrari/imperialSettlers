package cz.games.lp.backend.service;

import cz.games.lp.gamecore.components.enums.FactionTypes;

import java.util.List;
import java.util.UUID;

public interface FactionService {

    List<FactionTypes> getRemainingFactions(UUID uuid);

    void resetFactionSelection(UUID uuid);

    void selectFactionForCurrentPlayer(UUID uuid, FactionTypes faction);
}
