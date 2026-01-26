package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CardServiceImpl implements CardService {

    @Override
    public void prepareNewCommonCardDeck() {
        log.debug("prepareNewCommonCardDeck");
    }

    @Override
    public void prepareNewFactionCardDecks() {
        log.debug("prepareNewFactionCardDeck");
    }

    @Override
    public void dealFactionCardToPlayer() {
        log.debug("dealFactionCardToPlayer");
    }

    @Override
    public void dealCommonCardToPlayer() {
        log.debug("dealCommonCardToPlayer");
    }

    @Override
    public void dealInitialCardsToPlayers() {
        log.debug("dealInitialCardsToPlayers");
    }
}
