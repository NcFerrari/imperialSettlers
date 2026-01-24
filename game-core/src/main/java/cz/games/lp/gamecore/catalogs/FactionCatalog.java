package cz.games.lp.gamecore.catalogs;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.FactionTypes;

import java.util.Map;

public record FactionCatalog(Map<FactionTypes, FactionDTO> factionMap) {
}
