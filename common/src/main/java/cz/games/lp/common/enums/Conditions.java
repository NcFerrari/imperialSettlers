package cz.games.lp.common.enums;

import lombok.Getter;

@Getter
public enum Conditions {

    CHOICE_FROM_2_FACTION_CARDS(null, 2, null),
    HAS_SAMURAI_3_MAX(null, 3, null),
    HAS_PINK_3_MAX(Colors.PINK, 3, null),
    HAS_GRAY_3_MAX(Colors.GRAY, 3, null),
    HAS_RED_3_MAX(Colors.RED, 3, null),
    HAS_GOLD_3_MAX(Colors.GOLD, 3, null),
    HAS_BROWN_3_MAX(Colors.BROWN, 3, null),
    HAS_BLACK_COLOR_6_MAX(Colors.BLACK, 6, null),
    HAS_RED_6_MAX_AND_GET_CARD(Colors.RED, 6, Sources.CARD),
    HAS_PINK_6_MAX_AND_GET_CARD(Colors.PINK, 6, Sources.CARD),
    HAS_GOLD_COLOR_6_MAX_AND_GET_CARD(Colors.GOLD, 6, Sources.CARD),
    HAS_GRAY_6_MAX_AND_GET_CARD(Colors.GRAY, 6, Sources.CARD),
    HAS_BROWN_6_MAX_AND_GET_CARD(Colors.BROWN, 6, Sources.CARD);

    private final Colors color;
    private final int limit;
    private final Sources source;

    Conditions(Colors color, int limit, Sources source) {
        this.color = color;
        this.limit = limit;
        this.source = source;
    }
}
