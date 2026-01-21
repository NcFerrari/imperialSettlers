package cz.games.lp.gamecore;

import cz.games.lp.common.enums.RoundPhases;
import cz.games.lp.gamecore.components.CardDeck;
import cz.games.lp.gamecore.components.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class GameManager {

    private final Random random = new Random();
    private final List<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private int currentPlayerIndex;
    private RoundPhases currentPhase;
    private int roundNumber;
    private CardDeck commonCardDeck;

    public void addPlayer() {
        players.add(new Player());
    }

    public void setFirstPlayer() {
        currentPlayerIndex = random.nextInt(players.size());
        nextPlayer();
    }

    private void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }
        currentPlayer = players.get(currentPlayerIndex);
    }
}
