package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.catalogs.CardCatalog;
import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.components.CardDeck;
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

    public void dealCardsToPlayers(Player player, int factionCardCount, int commonCardCount) {
        IntStream.range(0, factionCardCount).forEach(i -> dealFactionCard(player));
        IntStream.range(0, commonCardCount).forEach(i -> dealCommonCard(player));
    }

    private void dealFactionCard(Player player) {
        dealCard(player, player.getFactionCardDeck());
    }

    private void dealCommonCard(Player player) {
//        dealCard(player, commonCardDeck);
    }

    private Card dealCard(Player player, CardDeck cardDeck) {
//        Card card = cardDeck.dealNextCard();
//        if (card != null) {
//            player.getCardsInHand().add(card);
//            return card;
//        }
        return null;
    }

    public Card getCard(String cardId) {
//        return cardCatalog.cardMap().get(cardId);
        return null;
    }

    public void performLookoutPhase() {
//        gameRoom.setCurrentPhase(RoundPhases.LOOKOUT);
//        dealCardsToAllPlayers(1, 2);
//        //mocking
//        Player player = gameRoom.getCurrentPlayer();
//        cardCatalog.cardMap()
//                .entrySet()
//                .stream()
//                .filter(entry -> entry.getKey().startsWith(player.getFaction().getFactionType().getCardTypes().getCardPrefix()))
//                .forEach(entry -> player.getBuiltLocations().get(entry.getValue().getCardCategory()).add(entry.getValue()));
    }


}
