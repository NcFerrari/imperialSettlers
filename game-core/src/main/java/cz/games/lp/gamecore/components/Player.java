package cz.games.lp.gamecore.components;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.CardCategories;
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

    private static final Sources[] PLAYERS_SOURCES = new Sources[]{Sources.SETTLER, Sources.WOOD, Sources.STONE, Sources.FOOD, Sources.COIN, Sources.SWORD, Sources.SHIELD};

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
        factionCardDeck = new CardDeck(factionDTO.getFactionType().getCardPrefix(), gameManager.getFactionCardDeckCount(), gameManager);
    }

    public void setUpOwnSources() {
        if (getFaction() == null) {
            return;
        }
        ownSources.clear();
        Stream.of(PLAYERS_SOURCES).forEach(source -> ownSources.put(source, 0));
        if (EnumSet.of(FactionTypes.EGYPT_F, FactionTypes.EGYPT_M).contains(getFaction().getFactionType())) {
            ownSources.put(Sources.EGYPT_TOKEN, 0);
        }
    }

    public CardDTO dealFactionCard() {
        return dealCard(factionCardDeck);
    }

    public CardDTO dealCommonCard() {
        return dealCard(gameManager.getCommonCardDeck());
    }

    private CardDTO dealCard(CardDeck cardDeck) {
        CardDTO newCard = cardDeck.dealNextCard();
        if (newCard != null) {
            cardsInHand.add(newCard);
        }
        return newCard;
    }

    public void dealFirstCards() {
        dealFactionCard();
        dealFactionCard();
        dealCommonCard();
        dealCommonCard();
    }

    public void performLookoutPhase() {
        dealFactionCard();
        dealCommonCard();
        dealCommonCard();
    }

    public void performProductionPhase() {
        productionFromCards();
        productionFromDeals();
        productionFromFactionBoard();
    }

    private void productionFromCards() {
        builtLocations
                .stream()
                .filter(card -> CardCategories.PRODUCTION.equals(card.getCardCategory()))
                .forEach(card -> card.getCardEffect()
                        .forEach(effect -> ownSources
                                .merge(effect.getSource(), 1, Integer::sum)
                        )
                );
    }

    private void productionFromDeals() {
        deals.forEach(card -> ownSources.merge(card.getDealSource(), 1, Integer::sum));
    }

    private void productionFromFactionBoard() {
        faction.getFactionProduction().forEach(source -> ownSources.merge(source, 1, Integer::sum));
    }
}
