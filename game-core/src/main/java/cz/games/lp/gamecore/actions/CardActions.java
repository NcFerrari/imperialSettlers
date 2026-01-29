package cz.games.lp.gamecore.actions;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.gamecore.GameManager;
import cz.games.lp.gamecore.catalogs.CardCatalog;
import cz.games.lp.gamecore.components.CardDeck;
import cz.games.lp.gamecore.components.Player;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.stream.IntStream;

@Getter
public class CardActions {

    private final CardCatalog cardCatalog = new CardCatalog(new LinkedHashMap<>());
    private final CardDeck commonCardDeck;
    private final GameManager gameManager;

    public CardActions(int commonCardDeckCount, GameManager gameManager) {
        commonCardDeck = new CardDeck(CardTypes.COMMON.getCardPrefix(), commonCardDeckCount, this);
        this.gameManager = gameManager;
    }

    public CardDTO getCard(String cardId) {
        return cardCatalog.cardMap().get(cardId);
    }

    public void createNewCardDeck() {
        commonCardDeck.createNewCardDeck();
    }

    public void performLookoutPhase() {
        dealCardsToAllPlayers(1, 2);
    }

    public void dealFirstCardsToAllPlayers() {
        dealCardsToAllPlayers(2, 2);
    }

    private void dealCardsToAllPlayers(int factionCardCount, int commonCardCount) {
        IntStream.range(0, factionCardCount).forEach(i -> dealFactionCard(gameManager.getCurrentPlayer()));
        IntStream.range(0, commonCardCount).forEach(i -> dealCommonCard(gameManager.getCurrentPlayer()));
        gameManager.nextPlayer();
        if (gameManager.allPlayersHaveBeenProcessed()) {
            return;
        }
        dealCardsToAllPlayers(factionCardCount, commonCardCount);
    }

    public CardDTO dealFactionCard(Player player) {
        return dealCard(player, player.getFactionCardDeck());
    }

    public CardDTO dealCommonCard(Player player) {
        return dealCard(player, commonCardDeck);
    }

    private CardDTO dealCard(Player player, CardDeck cardDeck) {
        CardDTO card = cardDeck.dealNextCard();
        if (card != null) {
            player.getCardsInHand().add(card);
            return card;
        }
        return null;
    }
}
