package cz.games.lp.gamecore;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.Sources;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Player {

    private final Map<Sources, Integer> ownSources = new EnumMap<>(Sources.class);
    private final List<CardDTO> cardsInHand = new ArrayList<>();
    private final List<CardDTO> builtLocations = new ArrayList<>();
    private CardDeck factionCardDeck;
    private FactionDTO faction;
    private int victoryPoints;

    public Player(FactionDTO faction) {
        this.faction = faction;
    }
}
