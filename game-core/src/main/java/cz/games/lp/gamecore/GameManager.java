package cz.games.lp.gamecore;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.common.enums.RoundPhases;
import cz.games.lp.gamecore.catalogs.CardCatalog;
import cz.games.lp.gamecore.components.CardDeck;
import cz.games.lp.gamecore.components.Player;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class GameManager {

    private static final int FACTION_CARD_DECK_COUNT = 30;
    private static final int COMMON_CARD_DECK_COUNT = 84;

    @Getter(AccessLevel.NONE)
    private final Random random = new Random();
    private final List<Player> players = new ArrayList<>();
    private final CardDeck commonCardDeck;
    private Player currentPlayer;
    private Player firstPlayer;
    @Setter
    @Getter(AccessLevel.NONE)
    private CardCatalog cardCatalog;
    @Setter
    private RoundPhases currentPhase;
    @Setter
    private int roundNumber;
    private int currentPlayerIndex;

    public GameManager() {
        commonCardDeck = new CardDeck(CardTypes.COMMON.getCardPrefix(), COMMON_CARD_DECK_COUNT, this);
    }

    public int getFactionCardDeckCount() {
        return FACTION_CARD_DECK_COUNT;
    }

    public void newGame() {
        currentPhase = RoundPhases.LOOKOUT;
        roundNumber = 1;
        commonCardDeck.createNewCardDeck();
    }

    public void addPlayer() {
        players.add(new Player(this));
    }

    public void setFirstPlayer() {
        currentPlayerIndex = random.nextInt(players.size());
        nextPlayer();
        firstPlayer = currentPlayer;
    }

    public void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }
        currentPlayer = players.get(currentPlayerIndex);
    }

    public CardDTO getCard(String cardId) {
        return cardCatalog.cardMap().get(cardId);
    }
}
