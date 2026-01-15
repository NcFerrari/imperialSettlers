package cz.games.lp.gamecore.service;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.Factions;

import java.util.Map;

public interface FactionService {

    Map<Factions, FactionDTO> getFactionMap();
}
