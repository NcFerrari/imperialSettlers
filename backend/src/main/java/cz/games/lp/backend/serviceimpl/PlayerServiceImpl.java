package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.FactionService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.gamecore.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
@Service
public class PlayerServiceImpl implements PlayerService {

    private final List<Player> players = new ArrayList<>();
    private final CardService cardService;
    private final FactionService factionService;

    private Player activePlayer;

    public PlayerServiceImpl(CardService cardService, FactionService factionService) {
        this.cardService = cardService;
        this.factionService = factionService;
    }

    @Override
    public void initializePlayers() {
        log.debug("addPlayers");
    }
}
