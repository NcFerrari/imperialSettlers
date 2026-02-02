package cz.games.lp.gamecore;

import cz.games.lp.gamecore.components.enums.FactionTypes;
import cz.games.lp.gamecore.components.enums.RoundPhases;
import cz.games.lp.gamecore.components.enums.Sources;
import cz.games.lp.gamecore.components.Player;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Getter
public class GameRoom {

    private static final int FACTION_CARD_DECK_COUNT = 30;
    private static final int COMMON_CARD_DECK_COUNT = 84;
    private static final Sources[] PLAYERS_BASIC_SOURCES = new Sources[]{
            Sources.SETTLER,
            Sources.WOOD,
            Sources.STONE,
            Sources.FOOD,
            Sources.COIN,
            Sources.SWORD,
            Sources.SHIELD
    };
    private final UUID id = UUID.randomUUID();
    private final List<FactionTypes> remainingFactions = new ArrayList<>();

    @Getter(AccessLevel.NONE)
    private final Random random = new Random();
    private final List<Player> players = new ArrayList<>();
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

    public Sources[] getplayersBasicSources() {
        return PLAYERS_BASIC_SOURCES;
    }

    public void newGame() {
        setCurrentPhase(RoundPhases.LOOKOUT);
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

    public void resetFactionSelection() {
        remainingFactions.clear();
        remainingFactions.add(FactionTypes.BARBARIAN_F);
        remainingFactions.add(FactionTypes.BARBARIAN_M);
        remainingFactions.add(FactionTypes.JAPAN_F);
        remainingFactions.add(FactionTypes.JAPAN_M);
        remainingFactions.add(FactionTypes.ROMAN_F);
        remainingFactions.add(FactionTypes.ROMAN_M);
        remainingFactions.add(FactionTypes.EGYPT_F);
        remainingFactions.add(FactionTypes.EGYPT_M);
    }

    public void removeFromChoice(FactionTypes faction) {
        int factionIndex = remainingFactions.indexOf(faction);
        if (factionIndex % 2 == 1) {
            factionIndex--;
        }
        int index = factionIndex;
        IntStream.range(0, 2).forEach(i -> remainingFactions.remove(index));
    }
}
