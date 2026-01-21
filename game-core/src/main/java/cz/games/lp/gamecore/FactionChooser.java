package cz.games.lp.gamecore;

import cz.games.lp.common.enums.FactionTitles;
import lombok.Getter;

import java.util.List;
import java.util.stream.IntStream;

public class FactionChooser {

    @Getter
    public List<FactionTitles> remainingFactions;

    public void newGame() {
        remainingFactions = List.of(
                FactionTitles.BARBARIAN_F,
                FactionTitles.BARBARIAN_M,
                FactionTitles.JAPAN_F,
                FactionTitles.JAPAN_M,
                FactionTitles.ROMAN_F,
                FactionTitles.ROMAN_M,
                FactionTitles.EGYPT_F,
                FactionTitles.EGYPT_M
        );
    }

    public void choiceFaction(FactionTitles faction) {
        int factionIndex = remainingFactions.indexOf(faction);
        if (factionIndex % 2 == 0) {
            factionIndex--;
        }
        int index = factionIndex;
        IntStream.range(0, 2).forEach(i -> remainingFactions.remove(index));
    }
}
