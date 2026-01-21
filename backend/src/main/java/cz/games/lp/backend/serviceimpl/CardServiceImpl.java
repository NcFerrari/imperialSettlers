package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.gamecore.catalogs.CardCatalog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private void dealCardToPlayer(List<String> cardDeck) {
        log.debug("dealCardToPlayer");
        cardDeck.removeFirst();
    }

    private List<String> generateShuffledCardDeck(CardTypes type, int cardCount) {
        log.debug("generateShuffledCardDeck");
        List<String> cardDeck = IntStream.range(0, cardCount).mapToObj(number -> type.getCardPrefix() + (number < 10 ? "00" : "0") + number).collect(Collectors.toList());
        Collections.shuffle(cardDeck);
        return cardDeck;
    }
}
