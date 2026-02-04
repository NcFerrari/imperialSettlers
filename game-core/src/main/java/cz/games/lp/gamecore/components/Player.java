package cz.games.lp.gamecore.components;

import cz.games.lp.gamecore.components.enums.CardCategories;
import cz.games.lp.gamecore.components.enums.CardTypes;
import cz.games.lp.gamecore.components.enums.Sources;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Getter
@Setter
@ToString
public class Player {

    private final Map<Sources, Integer> ownSources = new EnumMap<>(Sources.class);
    private final List<Card> cardsInHand = new ArrayList<>();
    private final Map<CardCategories, List<Card>> builtLocations = new EnumMap<>(CardCategories.class);
    private final List<Card> deals = new ArrayList<>();
    private final UUID playerID = UUID.randomUUID();
    private final CardDeck factionCardDeck = new CardDeck(CardTypes.FACTION, CardDeck.FACTION_CARD_DECK_COUNT);
    private Faction faction;
    private int victoryPoints;

    public Player() {
        Stream.of(CardCategories.values()).forEach(category -> builtLocations.put(category, new ArrayList<>()));
    }
}
