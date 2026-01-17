package cz.games.lp.common.enums;

import lombok.Getter;

public enum CardEffects {

    OBTAIN_SWORD(Sources.SWORD),
    OBTAIN_SETTLER(Sources.SETTLER),
    OBTAIN_STONE(Sources.STONE),
    OBTAIN_WOOD(Sources.WOOD),
    OBTAIN_COMMON_CARD(Sources.COMMON_CARD),
    OBTAIN_FACTION_CARD(Sources.FACTION_CARD),
    OBTAIN_COIN(Sources.COIN),
    OBTAIN_FOOD(Sources.FOOD),
    SCORE_POINT(Sources.VICTORY_POINT),
    OBTAIN_CARD(Sources.CARD),
    SCORE_TWO_POINTS(Sources.VICTORY_POINT),
    PRODUCE_ANOTHER_PRODUCTION(null),
    OBTAIN_TWO_SAMURAIS(null),
    OBTAIN_TWO_CARDS(null),
    OBTAIN_TWO_SETTLERS(null),
    SCORE_FOUR_POINTS(null);

    @Getter
    private final Sources source;

    CardEffects(Sources source) {
        this.source = source;
    }
}
