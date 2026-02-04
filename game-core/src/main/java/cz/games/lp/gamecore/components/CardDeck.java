package cz.games.lp.gamecore.components;

import cz.games.lp.gamecore.components.enums.CardTypes;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CardDeck {

    public static final int FACTION_CARD_DECK_COUNT = 30;
    public static final int COMMON_CARD_DECK_COUNT = 84;

    private final List<Integer> cards = new ArrayList<>();
    private final int cardCount;
    private CardTypes cardPrefix;

    public CardDeck(CardTypes cardPrefix, int cardCount) {
        this.cardPrefix = cardPrefix;
        this.cardCount = cardCount;
    }
}
