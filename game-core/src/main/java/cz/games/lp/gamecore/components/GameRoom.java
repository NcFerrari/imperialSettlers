package cz.games.lp.gamecore.components;

import cz.games.lp.gamecore.components.enums.CardTypes;
import cz.games.lp.gamecore.components.enums.FactionTypes;
import cz.games.lp.gamecore.components.enums.RoundPhases;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class GameRoom {

    private final UUID roomID = UUID.randomUUID();
    private final List<FactionTypes> remainingFactions = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();
    private final CardDeck commonCardDeck = new CardDeck(CardTypes.COMMON, CardDeck.COMMON_CARD_DECK_COUNT);

    private RoundPhases currentPhase;
    private int roundNumber;
    private Player currentPlayer;
    private Player firstPlayer;
    private int currentPlayerIndex;
}
