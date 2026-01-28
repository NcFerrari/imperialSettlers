package cz.games.lp.backend.service;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.FactionTypes;

import java.util.List;

public interface FactionService {

    List<FactionTypes> getRemainingFactions();

    void resetFactionSelection();

    FactionDTO getFactionFromCurrentPlayer();

    void selectFactionForCurrentPlayer(FactionTypes faction);
}
