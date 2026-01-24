package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.gamecore.catalogs.CardCatalog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class CardServiceImpl implements CardService {

    private final CardCatalog cardCatalog;

    public CardServiceImpl(CardCatalog cardCatalog) {
        this.cardCatalog = cardCatalog;
    }

    @Override
    public Map<String, CardDTO> getCardMap() {
        log.debug("getCardMap");
        return cardCatalog.cardMap();
    }

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
