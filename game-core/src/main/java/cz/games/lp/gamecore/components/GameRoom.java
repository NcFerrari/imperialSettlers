package cz.games.lp.gamecore.components;

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

    private final UUID id = UUID.randomUUID();
    private final List<FactionTypes> remainingFactions = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();

    private CardDeck commonCardDeck;
    private RoundPhases currentPhase;
    private int roundNumber;
    private Player currentPlayer;
    private Player firstPlayer;
    private int currentPlayerIndex;
}
