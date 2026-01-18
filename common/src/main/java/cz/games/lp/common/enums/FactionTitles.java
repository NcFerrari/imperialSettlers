package cz.games.lp.common.enums;

import lombok.Getter;

@Getter
public enum FactionTitles {

    BARBARIAN_F("barbarian_f", "barbarian_token", "barbarian", CardTypes.BARBARIAN),
    BARBARIAN_M("barbarian_m", "barbarian_token", "barbarian", CardTypes.BARBARIAN),
    JAPAN_F("japan_f", "japan_token", "japan", CardTypes.JAPAN),
    JAPAN_M("japan_m", "japan_token", "japan", CardTypes.JAPAN),
    ROMAN_F("roman_f", "roman_token", "roman", CardTypes.ROMAN),
    ROMAN_M("roman_m", "roman_token", "roman", CardTypes.ROMAN),
    EGYPT_F("egypt_f", "egypt_token", "egypt", CardTypes.EGYPT),
    EGYPT_M("egypt_m", "egypt_token", "egypt", CardTypes.EGYPT);

    private final String boardImage;
    private final String tokenImage;
    private final String factionCardPath;
    private final CardTypes cardPrefix;

    FactionTitles(String boardImage, String tokenImage, String factionCardPath, CardTypes cardPrefix) {
        this.boardImage = boardImage;
        this.tokenImage = tokenImage;
        this.factionCardPath = factionCardPath;
        this.cardPrefix = cardPrefix;
    }
}
