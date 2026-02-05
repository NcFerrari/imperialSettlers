package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.catalogs.CardCatalog;
import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.components.CardDeck;
import cz.games.lp.gamecore.components.GameRoom;
import cz.games.lp.gamecore.components.Player;
import lombok.Getter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class CardActions {

    private final CardCatalog cardCatalog = new CardCatalog(new LinkedHashMap<>());

    public void createNewCardDeck(CardDeck cardDeck) {
        List<Integer> integers = IntStream.range(1, cardDeck.getCardCount() + 1).boxed().collect(Collectors.toList());
        Collections.shuffle(integers);
        cardDeck.getCards().clear();
        cardDeck.getCards().addAll(integers);
    }

    public String dealFactionCards(Player player) {
        return dealCard(player, player.getFactionCardDeck());
    }

    public String dealCommonCards(Player player, GameRoom room) {
        return dealCard(player, room.getCommonCardDeck());
    }

    private String dealCard(Player player, CardDeck cardDeck) {
        int cardNumber = cardDeck.getCards().getFirst();
        String cardKey = cardDeck.getCardPrefix().getCardPrefix() + (cardNumber < 10 ? "00" : "0") + cardNumber;
        Card card = cardCatalog.cardMap().get(cardKey);
        if (card != null) {
            player.getCardsInHand().add(card);
            cardDeck.getCards().removeFirst();
            return cardKey;
        }
        return null;
    }
}
