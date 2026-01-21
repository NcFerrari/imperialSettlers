package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.gamecore.Player;
import cz.games.lp.common.enums.CardTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class CardServiceImpl implements CardService {

    private static final int FACTION_CARD_COUNT = 30;
    private static final int COMMON_CARD_COUNT = 84;

    private Map<Player, List<String>> playerFactionCardDecks = new HashMap<>();
    private List<String> commonCardDeck = new ArrayList<>();

    @Override
    public void prepareNewCommonCardDeck() {
        log.debug("prepareNewCommonCardDeck");
        commonCardDeck.clear();
        commonCardDeck = generateShuffledCardDeck(CardTypes.COMMON, COMMON_CARD_COUNT);
    }

    @Override
    public void prepareNewFactionCardDecks(List<Player> players) {
        log.debug("prepareNewFactionCardDeck");
        playerFactionCardDecks.clear();
        playerFactionCardDecks = players.stream()
                .collect(Collectors.toMap(Function.identity(), player -> generateShuffledCardDeck(player.getFaction().getFactionTitle().getCardPrefix(), FACTION_CARD_COUNT)));
    }

    @Override
    public void dealFactionCardToPlayer() {
        log.debug("dealFactionCardToPlayer");
    }

    @Override
    public void dealCommonCardToPlayer() {
        log.debug("dealCommonCardToPlayer");
        dealCardToPlayer(commonCardDeck);
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
