package cz.games.lp.gamecore.components;

import cz.games.lp.gamecore.components.enums.CardCategories;
import cz.games.lp.gamecore.components.enums.FactionTypes;
import cz.games.lp.gamecore.components.enums.Sources;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Getter
@Setter
@ToString
public class Player {

    private final Map<Sources, Integer> ownSources = new EnumMap<>(Sources.class);
    private final List<Card> cardsInHand = new ArrayList<>();
    private final Map<CardCategories, List<Card>> builtLocations = new EnumMap<>(CardCategories.class);
    private final List<Card> deals = new ArrayList<>();
    private CardDeck factionCardDeck;
    private Faction faction;
    private int victoryPoints;

    public Player() {
        Stream.of(CardCategories.values()).forEach(category -> builtLocations.put(category, new ArrayList<>()));
    }

    public void newGame() {
        ownSources.replaceAll((sources, value) -> 0);
        cardsInHand.clear();
        builtLocations.forEach((key, value) -> value.clear());
        deals.clear();
        factionCardDeck.createNewCardDeck();
        setVictoryPoints(0);
    }

    public void setUpOwnSources(Sources[] playersBasicSources) {
        if (getFaction() == null) {
            return;
        }
        ownSources.clear();
        Stream.of(playersBasicSources).forEach(source -> ownSources.put(source, 0));
        if (EnumSet.of(FactionTypes.EGYPT_F, FactionTypes.EGYPT_M).contains(getFaction().getFactionType())) {
            ownSources.put(Sources.EGYPT_TOKEN, 0);
        }
    }
}
