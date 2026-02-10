package cz.games.lp.common.game;

import cz.games.lp.common.enums.Factions;
import cz.games.lp.common.enums.Phases;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Setter
@Getter
public class GameData {

    private final int factionCardCount;
    private final int commonCardCount;
    private List<Integer> factionCards;
    private List<Integer> commonCards;
    private Factions selectedFaction;
    private int round;
    private Phases currentPhase;

    public GameData(int factionCardCount, int commonCardCount) {
        this.factionCardCount = factionCardCount;
        this.commonCardCount = commonCardCount;
    }

    public void newGame() {
        setRound(1);
        currentPhase = Phases.LOOKOUT;
        factionCards = fillCards(factionCardCount);
        commonCards = fillCards(commonCardCount);
    }

    private List<Integer> fillCards(int cardCount) {
        List<Integer> listOfCards = IntStream.range(1, cardCount + 1).boxed().collect(Collectors.toList());
        Collections.shuffle(listOfCards);
        return listOfCards;
    }

    public void nextRound() {
        setRound(getRound() + 1);
    }
}
