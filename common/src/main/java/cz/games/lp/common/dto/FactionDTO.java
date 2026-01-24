package cz.games.lp.common.dto;

import cz.games.lp.common.enums.FactionTitles;
import cz.games.lp.common.enums.Sources;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FactionDTO {

    private FactionTitles factionTitle;
    private List<Sources> factionProduction;
    private Sources saveSource;

    @Override
    public String toString() {
        return factionTitle.name();
    }
}
