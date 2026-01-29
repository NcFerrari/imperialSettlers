package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.gamecore.actions.CardActions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CardServiceImpl implements CardService {

    private final CardActions cardActions;
    private final PlayerService playerService;

    public CardServiceImpl(CardActions cardActions, PlayerService playerService) {
        this.cardActions = cardActions;
        this.playerService = playerService;
    }

    @Override
    public CardDTO dealFactionCardToCurrentPlayer() {
        log.debug("dealFactionCardToCurrentPlayer");
        return cardActions.dealFactionCard(playerService.getCurrentPlayer());
    }

    @Override
    public CardDTO dealCommonCardToCurrentPlayer() {
        log.debug("dealCommonCardToCurrentPlayer");
        return cardActions.dealCommonCard(playerService.getCurrentPlayer());
    }

    @Override
    public void dealFirstCardsToAllPlayers() {
        log.debug("dealFirstCardsToAllPlayers");
        cardActions.dealFirstCardsToAllPlayers();
    }
}
