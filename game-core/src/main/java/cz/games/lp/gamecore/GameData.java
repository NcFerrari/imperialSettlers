package cz.games.lp.gamecore;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.Factions;
import cz.games.lp.common.enums.RoundPhases;
import cz.games.lp.common.enums.Sources;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
public class GameData {

    private static final int FACTION_CARD_COUNT = 30;
    private static final int COMMON_CARD_COUNT = 84;
    private static final List<Sources> sourcesList = List.of(Sources.WOOD, Sources.STONE, Sources.FOOD, Sources.SETTLER, Sources.SWORD, Sources.SHIELD, Sources.GOLD);

    private final Map<Sources, Supply> ownSupplies = new EnumMap<>(Sources.class);
    private final List<Integer> factionCardDeck = new ArrayList<>();
    private final List<Integer> commonCardDeck = new ArrayList<>();
    private final List<CardDTO> cardsInHand = new ArrayList<>();
    private FactionDTO selectedFaction;
    private int scorePoints;
    private int round;
    private RoundPhases currentPhase;

    public void newGame() {
        if (getSelectedFaction() == null) {
            throw new IllegalArgumentException("No faction selected!");
        }

        setRound(1);
        setCurrentPhase(RoundPhases.LOOKOUT);
        setScorePoints(0);
        getCardsInHand().clear();

        getFactionCardDeck().clear();
        getFactionCardDeck().addAll(generateNewCardList(FACTION_CARD_COUNT));

        getCommonCardDeck().clear();
        getCommonCardDeck().addAll(generateNewCardList(COMMON_CARD_COUNT));

        getOwnSupplies().clear();
        sourcesList.forEach(source -> getOwnSupplies().put(source, new Supply(source)));
        if (EnumSet.of(Factions.EGYPT_F, Factions.EGYPT_M).contains(getSelectedFaction().getFaction())) {
            getOwnSupplies().put(Sources.EGYPT_TOKEN, new Supply(Sources.EGYPT_TOKEN));
        }
    }

    private List<Integer> generateNewCardList(int count) {
        List<Integer> cards = IntStream.range(0, count).boxed().collect(Collectors.toList());
        Collections.shuffle(cards);
        return cards;
    }

    public void addCardToHand(CardDTO card) {
        getCardsInHand().add(card);
    }
}
