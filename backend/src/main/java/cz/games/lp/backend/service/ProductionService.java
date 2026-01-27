package cz.games.lp.backend.service;

import cz.games.lp.common.dto.CardDTO;

public interface ProductionService {

    void produceFromCard(CardDTO card);
}
