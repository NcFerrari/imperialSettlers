package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.FactionService;
import cz.games.lp.backend.service.GameService;
import cz.games.lp.backend.service.GameSessionService;
import cz.games.lp.backend.service.PhaseService;
import cz.games.lp.backend.service.PlayerService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Agregating service for all game-related services
 */
@Slf4j
@Getter
@Service
public class GameServiceImpl implements GameService {

    private final CardService cardService;
    private final FactionService factionService;
    private final GameSessionService gameSessionService;
    private final PhaseService phaseService;
    private final PlayerService playerService;

    public GameServiceImpl(CardService cardService, FactionService factionService, GameSessionService gameSessionService, PhaseService phaseService, PlayerService playerService) {
        this.cardService = cardService;
        this.factionService = factionService;
        this.gameSessionService = gameSessionService;
        this.phaseService = phaseService;
        this.playerService = playerService;
    }
}
