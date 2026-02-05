package cz.games.lp.backend.serviceimpl.agregates;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.FactionService;
import cz.games.lp.backend.service.GameService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.backend.service.ProductionService;
import cz.games.lp.backend.service.agregates.GamePartsServices;
import lombok.Getter;
import org.springframework.stereotype.Service;

/**
 * Agregating service for all game-related services
 */
@Getter
@Service
public class GamePartsServicesImpl implements GamePartsServices {

    private final CardService cardService;
    private final FactionService factionService;
    private final PlayerService playerService;
    private final GameService gameService;
    private final ProductionService productionService;

    public GamePartsServicesImpl(CardService cardService, FactionService factionService, PlayerService playerService, GameService gameService, ProductionService productionService) {
        this.cardService = cardService;
        this.factionService = factionService;
        this.playerService = playerService;
        this.gameService = gameService;
        this.productionService = productionService;
    }
}
