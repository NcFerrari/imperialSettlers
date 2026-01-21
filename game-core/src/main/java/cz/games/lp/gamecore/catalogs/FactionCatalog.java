package cz.games.lp.gamecore.catalogs;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.FactionTitles;

import java.util.Map;

public record FactionCatalog(Map<FactionTitles, FactionDTO> factionMap) {
}
