package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.components.enums.CardCategories;
import cz.games.lp.gamecore.components.enums.ProductionStatus;
import cz.games.lp.gamecore.components.enums.RoundPhases;
import cz.games.lp.gamecore.components.enums.Sources;
import cz.games.lp.gamecore.GameRoom;

import java.util.ArrayList;
import java.util.List;

public class ProductionActions {

    private final List<Sources> sourceChoice = new ArrayList<>();
    private ProductionStatus productionStatus = ProductionStatus.PRODUCE_FROM_FACTION_LOCATIONS;

    public ProductionStatus performProductionPhase() {
        return null;
//        gameRoom.setCurrentPhase(RoundPhases.PRODUCTION);
//        switch (productionStatus) {
//            case ProductionStatus.PRODUCE_FROM_FACTION_LOCATIONS -> productionFromFactionProductionCards();
////            case ProductionStatus.PRODUCE_FROM_DEALS -> productionFromDeals();
////            case ProductionStatus.PRODUCE_FROM_FACTION_BOARD -> productionFromFactionBoard();
////            case ProductionStatus.PRODUCE_FROM_COMMON_LOCATIONS -> productionFromCards();
//        }
//        return productionStatus;
    }

    private void productionFromFactionProductionCards() {
//        gameRoom.getCurrentPlayer().getBuiltLocations().get(CardCategories.FACTION_PRODUCTION).forEach(card -> produceFromCard(card));
    }

    private void produceFromCard(Card card) {
        if (card.getCondition() != null) {

        } else if (!card.getOrEffect().isEmpty()) {

        } else {
//            card.getCardEffect().forEach(effect -> gameRoom.getCurrentPlayer().getOwnSources().computeIfPresent(effect.getSource(), (source, value) -> value + 1));
        }
    }

    private List<Sources> processProduction(Sources source) {
        sourceChoice.clear();
        switch (source) {
//            case FACTION_CARD -> cardActions.dealFactionCard(player);
//            case COMMON_CARD -> cardActions.dealCommonCard(player);
//            case VICTORY_POINT -> player.addVictoryPoint();
//            case CARD -> sourceChoice.addAll(List.of(Sources.FACTION_CARD, Sources.COMMON_CARD));
//            default -> player.getOwnSources().computeIfPresent(source, (sourceInMap, value) -> value + 1);
        }
        return sourceChoice;
    }
}