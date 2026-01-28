package cz.games.lp.gamecore;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.gamecore.catalogs.CardCatalog;
import cz.games.lp.gamecore.components.CardDeck;
import cz.games.lp.gamecore.components.Player;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.stream.IntStream;

@Getter
public class CardManager {

    private final CardCatalog cardCatalog = new CardCatalog(new LinkedHashMap<>());
    private final CardDeck commonCardDeck;
    private final GameManager gameManager;

    public CardManager(int commonCardDeckCount, GameManager gameManager) {
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

    public void dealFactionCard(Player player) {
        dealCard(player, player.getFactionCardDeck());
    }

    public void dealCommonCard(Player player) {
        dealCard(player, commonCardDeck);
    }

    private void dealCard(Player player, CardDeck cardDeck) {
        CardDTO card = cardDeck.dealNextCard();
        if (card != null) {
            player.getCardsInHand().add(card);
        }
    }
}
