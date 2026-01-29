package cz.games.lp.gamecore;

import cz.games.lp.common.enums.RoundPhases;
import cz.games.lp.gamecore.actions.FactionActions;
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
    private final FactionActions factionActions = new FactionActions();
    private Player currentPlayer;
    private Player firstPlayer;
    @Setter
    private RoundPhases currentPhase;
    @Setter
    private int roundNumber;
    private int currentPlayerIndex;

    public int getCommonCardDeckCount() {
        return COMMON_CARD_DECK_COUNT;
    }

    public int getFactionCardDeckCount() {
        return FACTION_CARD_DECK_COUNT;
    }

    public void newGame() {
        currentPhase = RoundPhases.LOOKOUT;
        roundNumber = 1;
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

    public boolean allPlayersHaveBeenProcessed() {
        return getCurrentPlayer().equals(getFirstPlayer());
    }
}
