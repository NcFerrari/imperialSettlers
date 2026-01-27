package cz.games.lp.gamecore;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.gamecore.catalogs.CardCatalog;
import cz.games.lp.gamecore.components.CardDeck;
import cz.games.lp.gamecore.components.Player;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.IntStream;

@Getter
public class CardManager {

    private final CardCatalog cardCatalog = new CardCatalog(new LinkedHashMap<>());
    private final CardDeck commonCardDeck;

    public CardManager(int commonCardDeckCount) {
        commonCardDeck = new CardDeck(CardTypes.COMMON.getCardPrefix(), commonCardDeckCount, this);
    }

    public CardDTO getCard(String cardId) {
        return cardCatalog.cardMap().get(cardId);
    }

    public void createNewCardDeck() {
        commonCardDeck.createNewCardDeck();
    }

    public void dealFirstCardsToAllPlayers(List<Player> players) {
        players.forEach(player -> {
            IntStream.range(0, 2).forEach(i -> dealFactionCard(player));
            IntStream.range(0, 2).forEach(i -> dealCommonCard(player));
        });
    }

    private void dealFactionCard(Player player) {
        dealCard(player, player.getFactionCardDeck());
    }

    private void dealCommonCard(Player player) {
        dealCard(player, commonCardDeck);
    }

    private void dealCard(Player player, CardDeck cardDeck) {
        CardDTO card = cardDeck.dealNextCard();
        if (card != null) {
            player.getCardsInHand().add(card);
        }
    }

    public void performLookoutPhase() {

    }
}
