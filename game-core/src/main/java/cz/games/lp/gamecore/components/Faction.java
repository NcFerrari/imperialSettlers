package cz.games.lp.gamecore.components;

import cz.games.lp.gamecore.components.enums.FactionTypes;
import cz.games.lp.gamecore.components.enums.Sources;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Faction {

    private FactionTypes factionType;
    private List<Sources> factionProduction;
    private Sources saveSource;
}
