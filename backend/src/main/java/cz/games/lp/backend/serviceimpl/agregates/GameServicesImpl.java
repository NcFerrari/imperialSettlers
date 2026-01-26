package cz.games.lp.backend.serviceimpl.agregates;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.FactionService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.backend.service.ProductionService;
import cz.games.lp.backend.service.agregates.GameServices;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Agregating service for all game-related services
 */
@Slf4j
@Getter
@Service
public class GameServicesImpl implements GameServices {

    private final CardService cardService;
    private final FactionService factionService;
    private final PlayerService playerService;
    private final ProductionService productionService;

    public GameServicesImpl(CardService cardService, FactionService factionService, PlayerService playerService, ProductionService productionService) {
        this.cardService = cardService;
        this.factionService = factionService;
        this.playerService = playerService;
        this.productionService = productionService;
    }
}
