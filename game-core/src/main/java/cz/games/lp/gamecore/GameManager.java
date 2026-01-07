package cz.games.lp.gamecore;

import cz.games.lp.common.enums.Factions;
import cz.games.lp.common.enums.RoundPhases;
import cz.games.lp.common.enums.Sources;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameManager {

    private static final int FACTION_CARD_COUNT = 30;
    private static final int COMMON_CARD_COUNT = 84;
    private static final List<Sources> sourcesList = List.of(Sources.WOOD, Sources.STONE, Sources.FOOD, Sources.SETTLER, Sources.SWORD, Sources.SHIELD, Sources.GOLD);

    public void newGame(GameData gameData) {

        if (gameData.getSelectedFaction() == null) {
            throw new IllegalArgumentException("No faction selected!");
        }

        gameData.setRound(1);
        gameData.setCurrentPhase(RoundPhases.LOOKOUT);
        gameData.setScorePoints(0);
        gameData.getCardsInHand().clear();

        gameData.getFactionCardDeck().clear();
        gameData.getFactionCardDeck().addAll(generateNewCardList(FACTION_CARD_COUNT));

        gameData.getCommonCardDeck().clear();
        gameData.getCommonCardDeck().addAll(generateNewCardList(COMMON_CARD_COUNT));

        gameData.getOwnSupplies().clear();
        sourcesList.forEach(source -> gameData.getOwnSupplies().put(source, new Supply(source)));
        if (EnumSet.of(Factions.EGYPT_F, Factions.EGYPT_M).contains(gameData.getSelectedFaction().getFaction())) {
            gameData.getOwnSupplies().put(Sources.EGYPT_TOKEN, new Supply(Sources.EGYPT_TOKEN));
        }
    }

    private List<Integer> generateNewCardList(int count) {
        List<Integer> cards = IntStream.range(0, count).boxed().collect(Collectors.toList());
        Collections.shuffle(cards);
        return cards;
    }
}
