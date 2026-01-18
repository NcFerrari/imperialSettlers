package cz.games.lp.backend.service;

import cz.games.lp.common.enums.RoundPhases;

public interface PhaseService {

    void setCurrentPhase(RoundPhases roundPhases);
}
