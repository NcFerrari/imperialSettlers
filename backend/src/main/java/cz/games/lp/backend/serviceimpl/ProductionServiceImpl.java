package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.ProductionService;
import org.springframework.stereotype.Service;

@Service
public class ProductionServiceImpl implements ProductionService {

    @Override
    public void performProductionPhase() {

    }
}

//public void performProductionPhase() {
//        productionFromCards();
//        productionFromDeals();
//        productionFromFactionBoard();
//    }
//
//    private void productionFromCards() {
//        builtLocations
//                .stream()
//                .filter(card -> CardCategories.PRODUCTION.equals(card.getCardCategory()))
//                .forEach(card -> card.getCardEffect()
//                        .forEach(effect ->
//                                ownSources.merge(effect.getSource(), 1, Integer::sum)
//                        )
//                );
//    }
//
//    private void productionFromDeals() {
//        deals.forEach(card -> ownSources.merge(card.getDealSource(), 1, Integer::sum));
//    }
//
//    private void productionFromFactionBoard() {
//        faction.getFactionProduction().forEach(source -> ownSources.merge(source, 1, Integer::sum));
//    }