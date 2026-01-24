package cz.games.lp.backend.serviceimpl.agregates;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.FactionService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.backend.service.agregates.GameService;
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
    private final PlayerService playerService;

    public GameServiceImpl(CardService cardService, FactionService factionService, PlayerService playerService) {
        this.cardService = cardService;
        this.factionService = factionService;
        this.playerService = playerService;
    }
}
