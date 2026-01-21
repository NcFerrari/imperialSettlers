package cz.games.lp.gamecore;

import cz.games.lp.common.enums.RoundPhases;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private final List<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private RoundPhases currentPhase;
    private int roundNumber;
    private CardDeck commonCardDeck;
}
