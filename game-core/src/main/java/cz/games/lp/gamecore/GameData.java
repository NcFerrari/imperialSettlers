package cz.games.lp.gamecore;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.RoundPhases;
import cz.games.lp.common.enums.Sources;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GameData {

    private final Map<Sources, Supply> ownSupplies = new EnumMap<>(Sources.class);
    private final List<Integer> factionCardDeck = new ArrayList<>();
    private final List<Integer> commonCardDeck = new ArrayList<>();
    private final List<CardDTO> cardsInHand = new ArrayList<>();
    private FactionDTO selectedFaction;
    private int scorePoints;
    private int round;
    private RoundPhases currentPhase;
}
