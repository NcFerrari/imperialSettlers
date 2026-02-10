package cz.games.lp.common.enums;

import lombok.Getter;

@Getter
public enum Conditions {
    FACTION_CARD_2(null, 2, null),
    SAMURAI_3(null, 3, null),
    BLACK_6(Colors.BLACK, 6, null),
    GOLD_6_CARD(Colors.GOLD, 6, Sources.CARD),
    PINK_3(Colors.PINK, 3, null),
    RED_6_CARD(Colors.RED, 6, Sources.CARD),
    PINK_6_CARD(Colors.PINK, 6, Sources.CARD),
    GRAY_3(Colors.GRAY, 3, null),
    RED_3(Colors.RED, 3, null),
    GOLD_3(Colors.GOLD, 3, null),
    BROWN_3(Colors.BROWN, 3, null),
    GRAY_6_CARD(Colors.GRAY, 6, Sources.CARD),
    BROWN_6_CARD(Colors.BROWN, 6, Sources.CARD);

    private final Colors color;
    private final int limit;
    private final Sources source;

    Conditions(Colors color, int limit, Sources source) {
        this.color = color;
        this.limit = limit;
        this.source = source;
    }
}
