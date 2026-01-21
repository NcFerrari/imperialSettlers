package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.GameSessionService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.common.enums.RoundPhases;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Slf4j
@Service
public class GameSessionServiceImpl implements GameSessionService {

    private final CardService cardService;
    private final PlayerService playerService;

    private int gameRound;

    public GameSessionServiceImpl(CardService cardService, PlayerService playerService) {
        this.cardService = cardService;
        this.playerService = playerService;
    }

    @Override
    public void initializeGame() {
        log.debug("newGame");
        setGameRound(1);
        cardService.prepareNewCommonCardDeck();
    }
}
