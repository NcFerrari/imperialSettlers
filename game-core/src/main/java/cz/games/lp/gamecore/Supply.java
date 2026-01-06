package cz.games.lp.gamecore;

import cz.games.lp.common.enums.Sources;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Supply {

    private final Sources sources;
    @Setter
    private int count;

    public Supply(Sources sources) {
        this.sources = sources;
    }

    public void incrementCount() {
        count++;
    }

    public void decrementCount() {
        count--;
    }
}
