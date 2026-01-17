package cz.games.lp.common.enums;

import lombok.Getter;

public enum CardEffects {

    SWORD(Sources.SWORD),
    SETTLER(Sources.SETTLER),
    STONE(Sources.STONE),
    WOOD(Sources.WOOD),
    COMMON_CARD(Sources.COMMON_CARD),
    FACTION_CARD(Sources.FACTION_CARD),
    GOLD(Sources.GOLD),
    FOOD(Sources.FOOD),
    SCORE_POINT(Sources.SCORE_POINT),
    CARD(Sources.CARD),
    TWO_SCORE_POINTS(Sources.SCORE_POINT),
    ANOTHER_PRODUCTION(null),
    TWO_SAMURAIS(null),
    TWO_CARDS(null),
    TWO_SETTLERS(null),
    FOUR_SCORE_POINTS(null);

    @Getter
    private final Sources source;

    CardEffects(Sources source) {
        this.source = source;
    }
}
