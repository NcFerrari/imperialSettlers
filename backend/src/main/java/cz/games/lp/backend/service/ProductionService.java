package cz.games.lp.backend.service;

import cz.games.lp.gamecore.actions.ProduceChoice;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductionService {

    Map<UUID, List<ProduceChoice>> performProductionPhase(UUID roomID);
}
