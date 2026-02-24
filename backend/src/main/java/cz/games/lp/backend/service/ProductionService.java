package cz.games.lp.backend.service;

import cz.games.lp.gamecore.actions.ProduceReport;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductionService {

    Map<UUID, List<ProduceReport>> produceFactionCards(UUID roomID);

    Map<UUID, List<ProduceReport>> produceDeals(UUID roomID);

    Map<UUID, ProduceReport> produceFactionBoard(UUID roomID);

    Map<UUID, List<ProduceReport>> produceCommonCards(UUID roomID);

    ProduceReport produceFromSingleCard(String cardID, UUID roomID, UUID playerID);
}
