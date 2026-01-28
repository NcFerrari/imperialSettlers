package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.gamecore.CardManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CardServiceImpl implements CardService {

    private final CardManager cardManager;
    private final PlayerService playerService;

    public CardServiceImpl(CardManager cardManager, PlayerService playerService) {
        this.cardManager = cardManager;
        this.playerService = playerService;
    }

    @Override
    public CardDTO dealFactionCardToCurrentPlayer() {
        log.debug("dealFactionCardToCurrentPlayer");
        return cardManager.dealFactionCard(playerService.getCurrentPlayer());
    }

    @Override
    public CardDTO dealCommonCardToCurrentPlayer() {
        log.debug("dealCommonCardToCurrentPlayer");
        return cardManager.dealCommonCard(playerService.getCurrentPlayer());
    }

    @Override
    public void dealFirstCardsToAllPlayers() {
        log.debug("dealFirstCardsToAllPlayers");
        cardManager.dealFirstCardsToAllPlayers();
    }
}
