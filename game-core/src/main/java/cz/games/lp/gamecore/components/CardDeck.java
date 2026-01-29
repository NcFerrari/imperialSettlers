package cz.games.lp.gamecore.components;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.gamecore.actions.CardActions;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CardDeck {

    private final CardActions cardActions;
    private final String cardPrefix;
    private final int cardCount;
    @Getter
    private List<Integer> cards;

    public CardDeck(String cardPrefix, int cardCount, CardActions cardActions) {
        this.cardPrefix = cardPrefix;
        this.cardActions = cardActions;
        this.cardCount = cardCount;
    }

    public void createNewCardDeck() {
        cards = IntStream.range(1, cardCount + 1).boxed().collect(Collectors.toList());
        Collections.shuffle(cards);
    }

    public CardDTO dealNextCard() {
        if (cards.isEmpty()) {
            return null;
        }
        String cardId = cardPrefix + (cards.getFirst() < 10 ? "00" : "0") + cards.getFirst();
        cards.removeFirst();
        return cardActions.getCard(cardId);
    }
}
