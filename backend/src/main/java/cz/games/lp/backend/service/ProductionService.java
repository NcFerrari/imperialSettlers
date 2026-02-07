package cz.games.lp.backend.service;

import java.util.UUID;

public interface ProductionService {

    void performProductionPhase(UUID roomID);
}
