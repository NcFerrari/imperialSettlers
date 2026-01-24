package cz.games.lp.gamecore;

import cz.games.lp.common.enums.FactionTitles;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class FactionChooser {

    @Getter
    public List<FactionTitles> remainingFactions;

    public void newGame() {
        remainingFactions = new ArrayList<>();
        remainingFactions.add(FactionTitles.BARBARIAN_F);
        remainingFactions.add(FactionTitles.BARBARIAN_M);
        remainingFactions.add(FactionTitles.JAPAN_F);
        remainingFactions.add(FactionTitles.JAPAN_M);
        remainingFactions.add(FactionTitles.ROMAN_F);
        remainingFactions.add(FactionTitles.ROMAN_M);
        remainingFactions.add(FactionTitles.EGYPT_F);
        remainingFactions.add(FactionTitles.EGYPT_M);
    }

    public void removeFromChoice(FactionTitles faction) {
        int factionIndex = remainingFactions.indexOf(faction);
        if (factionIndex % 2 == 1) {
            factionIndex--;
        }
        int index = factionIndex;
        IntStream.range(0, 2).forEach(i -> remainingFactions.remove(index));
    }
}
