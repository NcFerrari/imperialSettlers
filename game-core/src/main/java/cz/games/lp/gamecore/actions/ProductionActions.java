package cz.games.lp.gamecore.actions;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.enums.CardCategories;
import cz.games.lp.common.enums.ProductionStatus;
import cz.games.lp.common.enums.RoundPhases;
import cz.games.lp.common.enums.Sources;
import cz.games.lp.gamecore.GameManager;
import cz.games.lp.gamecore.components.Player;

import java.util.ArrayList;
import java.util.List;

public class ProductionActions {

    private final List<Sources> sourceChoice = new ArrayList<>();
    private final GameManager gameManager;
    private final CardActions cardActions;
    private ProductionStatus productionStatus = ProductionStatus.PRODUCE_FROM_FACTION_LOCATIONS;

    public ProductionActions(GameManager gameManager, CardActions cardActions) {
        this.gameManager = gameManager;
        this.cardActions = cardActions;
    }

    public ProductionStatus performProductionPhase() {
        gameManager.setCurrentPhase(RoundPhases.PRODUCTION);
        switch (productionStatus) {
            case ProductionStatus.PRODUCE_FROM_FACTION_LOCATIONS -> productionFromCards();
//            case ProductionStatus.PRODUCE_FROM_DEALS -> productionFromDeals();
//            case ProductionStatus.PRODUCE_FROM_FACTION_BOARD -> productionFromFactionBoard();
//            case ProductionStatus.PRODUCE_FROM_COMMON_LOCATIONS -> productionFromCards();
        }
        return productionStatus;
    }

    private void productionFromCards() {
//        gameManager.getCurrentPlayer().getBuiltLocations()
//                .stream()
//                .filter(card -> CardCategories.PRODUCTION.equals(card.getCardCategory()))
//                .forEach(card -> processProductionEffectsFromCard(player, card));
    }

//    private void productionFromDeals() {
//        player.getDeals().forEach(card -> processProduction(player, card.getDealSource()));
//    }
//
//    private void productionFromFactionBoard() {
//        player.getFaction().getFactionProduction().forEach(source -> processProduction(player, source));
//    }
//
//    private void processProductionEffectsFromCard(CardDTO card) {
//        card.getCardEffect().forEach(effect -> processProduction(player, effect.getSource()));
//    }
//
//    private List<Sources> processProduction(Sources source) {
//        sourceChoice.clear();
//        switch (source) {
//            case FACTION_CARD -> cardActions.dealFactionCard(player);
//            case COMMON_CARD -> cardActions.dealCommonCard(player);
//            case VICTORY_POINT -> player.addVictoryPoint();
//            case CARD -> sourceChoice.addAll(List.of(Sources.FACTION_CARD, Sources.COMMON_CARD));
//            default -> player.getOwnSources().computeIfPresent(source, (sourceInMap, value) -> value + 1);
//        }
//        return sourceChoice;
//    }
}