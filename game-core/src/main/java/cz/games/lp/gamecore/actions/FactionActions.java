package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.catalogs.FactionCatalog;
import lombok.Getter;

import java.util.LinkedHashMap;

@Getter
public class FactionActions {

    private final FactionCatalog factionCatalog = new FactionCatalog(new LinkedHashMap<>());
}
