package cz.games.lp.gamecore.components;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.FactionTypes;
import cz.games.lp.common.enums.Sources;
import cz.games.lp.gamecore.GameManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Getter
@Setter
public class Player {

    private static final Sources[] PLAYERS_BASIC_SOURCES = new Sources[]{
            Sources.SETTLER,
            Sources.WOOD,
            Sources.STONE,
            Sources.FOOD,
            Sources.COIN,
            Sources.SWORD,
            Sources.SHIELD
    };

    private final Map<Sources, Integer> ownSources = new EnumMap<>(Sources.class);
    private final List<CardDTO> cardsInHand = new ArrayList<>();
    private final List<CardDTO> builtLocations = new ArrayList<>();
    private final List<CardDTO> deals = new ArrayList<>();
    @Getter(AccessLevel.NONE)
    private final GameManager gameManager;
    private CardDeck factionCardDeck;
    private FactionDTO faction;
    private int victoryPoints;

    public Player(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void newGame() {
        ownSources.replaceAll((sources, value) -> 0);
        cardsInHand.clear();
        builtLocations.clear();
        factionCardDeck.createNewCardDeck();
        victoryPoints = 0;
    }

    public void selectFaction(FactionDTO factionDTO) {
        setFaction(factionDTO);
        factionCardDeck = new CardDeck(factionDTO.getFactionType().getCardPrefix(), gameManager.getFactionCardDeckCount(), cardActions);
    }

    public void setUpOwnSources() {
        if (getFaction() == null) {
            return;
        }
        ownSources.clear();
        Stream.of(PLAYERS_BASIC_SOURCES).forEach(source -> ownSources.put(source, 0));
        if (EnumSet.of(FactionTypes.EGYPT_F, FactionTypes.EGYPT_M).contains(getFaction().getFactionType())) {
            ownSources.put(Sources.EGYPT_TOKEN, 0);
        }
    }
}
