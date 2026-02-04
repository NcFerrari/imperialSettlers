package cz.games.lp.gamecore.components;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CardDeck {

    public static final int FACTION_CARD_DECK_COUNT = 30;
    public static final int COMMON_CARD_DECK_COUNT = 84;

    private final int cardCount;
    @Getter
    private final String cardPrefix;
    @Getter
    private List<Integer> cards;

    public CardDeck(String cardPrefix, int cardCount) {
        this.cardPrefix = cardPrefix;
        this.cardCount = cardCount;
    }

    public void createNewCardDeck() {
        cards = IntStream.range(1, cardCount + 1).boxed().collect(Collectors.toList());
        Collections.shuffle(cards);
    }
}
