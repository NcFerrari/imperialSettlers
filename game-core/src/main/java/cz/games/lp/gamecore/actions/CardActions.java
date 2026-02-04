package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.catalogs.CardCatalog;
import cz.games.lp.gamecore.components.CardDeck;
import cz.games.lp.gamecore.components.Player;
import lombok.Getter;

import java.util.LinkedHashMap;

@Getter
public class CardActions {

    private final CardCatalog cardCatalog = new CardCatalog(new LinkedHashMap<>());

    public Card getCard(String cardId) {
        return cardCatalog.cardMap().get(cardId);
    }

    public void createNewCardDeck() {
//        commonCardDeck.createNewCardDeck();
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

    public void dealFirstCardsToAllPlayers() {
        dealCardsToAllPlayers(2, 2);
    }

    private void dealCardsToAllPlayers(int factionCardCount, int commonCardCount) {
//        IntStream.range(0, factionCardCount).forEach(i -> dealFactionCard(gameRoom.getCurrentPlayer()));
//        IntStream.range(0, commonCardCount).forEach(i -> dealCommonCard(gameRoom.getCurrentPlayer()));
//        gameRoom.nextPlayer();
//        if (gameRoom.allPlayersHaveBeenProcessed()) {
//            return;
//        }
//        dealCardsToAllPlayers(factionCardCount, commonCardCount);
    }

    public Card dealFactionCard(Player player) {
//        return dealCard(player, player.getFactionCardDeck());
        return null;
    }

    public Card dealCommonCard(Player player) {
        return null;
//        return dealCard(player, commonCardDeck);
    }

//    private Card dealCard(Player player, CardDeck cardDeck) {
//        Card card = cardDeck.dealNextCard();
//        if (card != null) {
//            player.getCardsInHand().add(card);
//            return card;
//        }
//        return null;
//    }
}
