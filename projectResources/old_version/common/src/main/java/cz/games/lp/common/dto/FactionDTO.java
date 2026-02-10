package cz.games.lp.common.dto;

import cz.games.lp.common.enums.Factions;
import cz.games.lp.common.enums.Sources;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FactionDTO {

    private Factions faction;
    private List<Sources> factionProduction;
    private Sources saveSource;
}
