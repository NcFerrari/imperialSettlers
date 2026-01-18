package cz.games.lp.common.enums;

import lombok.Getter;

public enum CardTypes {

    FACTION(null),
    BARBARIAN("bar"),
    JAPAN("jap"),
    ROMAN("rom"),
    EGYPT("egy"),
    COMMON("com");

    @Getter
    private final String cardPrefix;

    CardTypes(String cardPrefix) {
        this.cardPrefix = cardPrefix;
    }
}
