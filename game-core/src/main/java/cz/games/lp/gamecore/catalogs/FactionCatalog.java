package cz.games.lp.gamecore.catalogs;

import cz.games.lp.gamecore.components.Faction;
import cz.games.lp.gamecore.components.enums.FactionTypes;

import java.util.Map;

public record FactionCatalog(Map<FactionTypes, Faction> factionMap) {
}
