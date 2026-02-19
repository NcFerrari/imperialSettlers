package cz.games.lp.backend.service;

import cz.games.lp.gamecore.actions.ProduceChoice;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductionService {

    Map<UUID, List<ProduceChoice>> produceFactionCards(UUID roomID);

    Map<UUID, List<ProduceChoice>> produceDeals(UUID roomID);

    Map<UUID, ProduceChoice> produceFactionBoard(UUID roomID);

    Map<UUID, List<ProduceChoice>> produceCommonCards(UUID roomID);
}
