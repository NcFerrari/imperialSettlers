package cz.games.lp.common.dto;

import cz.games.lp.common.enums.FactionTypes;
import cz.games.lp.common.enums.Sources;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FactionDTO {

    private FactionTypes factionType;
    private List<Sources> factionProduction;
    private Sources saveSource;

    @Override
    public String toString() {
        return factionType.name();
    }
}
