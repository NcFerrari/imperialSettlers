package cz.games.lp.gamecore;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.RoundPhases;
import cz.games.lp.common.enums.Sources;

import java.util.List;
import java.util.Map;

public record GameData(
        FactionDTO selectedFaction,
        List<Integer> factionCardDeck,
        List<Integer> commonCardDeck,
        int scorePoints,
        List<CardDTO> cardsInHand,
        int round,
        RoundPhases currentPhase,
        Map<Sources, Supply> ownSupplies) {
}
