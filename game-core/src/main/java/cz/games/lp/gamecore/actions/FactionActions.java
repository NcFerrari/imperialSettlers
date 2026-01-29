package cz.games.lp.gamecore.actions;

import cz.games.lp.common.enums.FactionTypes;
import cz.games.lp.gamecore.catalogs.FactionCatalog;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.IntStream;

@Getter
public class FactionActions {

    private final FactionCatalog factionCatalog = new FactionCatalog(new LinkedHashMap<>());
    private List<FactionTypes> remainingFactions;

    public void resetFactionSelection() {
        remainingFactions = new ArrayList<>();
        remainingFactions.add(FactionTypes.BARBARIAN_F);
        remainingFactions.add(FactionTypes.BARBARIAN_M);
        remainingFactions.add(FactionTypes.JAPAN_F);
        remainingFactions.add(FactionTypes.JAPAN_M);
        remainingFactions.add(FactionTypes.ROMAN_F);
        remainingFactions.add(FactionTypes.ROMAN_M);
        remainingFactions.add(FactionTypes.EGYPT_F);
        remainingFactions.add(FactionTypes.EGYPT_M);
    }

    public void removeFromChoice(FactionTypes faction) {
        int factionIndex = remainingFactions.indexOf(faction);
        if (factionIndex % 2 == 1) {
            factionIndex--;
        }
        int index = factionIndex;
        IntStream.range(0, 2).forEach(i -> remainingFactions.remove(index));
    }
}
