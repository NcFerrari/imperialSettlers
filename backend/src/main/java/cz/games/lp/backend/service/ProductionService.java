package cz.games.lp.backend.service;

import cz.games.lp.gamecore.actions.ProduceResult;
import cz.games.lp.gamecore.components.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductionService {

    Map<Player, List<ProduceResult>> performProductionPhase(UUID roomID);
}
