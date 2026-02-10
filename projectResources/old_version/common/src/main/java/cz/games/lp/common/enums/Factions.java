package cz.games.lp.common.enums;

import lombok.Getter;

@Getter
public enum Factions {
    BARBARIAN_F("barbarian_f", "barbarian_token", "barbarian"),
    BARBARIAN_M("barbarian_m", "barbarian_token", "barbarian"),
    JAPAN_F("japan_f", "japan_token", "japan"),
    JAPAN_M("japan_m", "japan_token", "japan"),
    ROMAN_F("roman_f", "roman_token", "roman"),
    ROMAN_M("roman_m", "roman_token", "roman"),
    EGYPT_F("egypt_f", "egypt_token", "egypt"),
    EGYPT_M("egypt_m", "egypt_token", "egypt");

    private final Sources[] sourcesInOwnSupply = {Sources.SETTLER, Sources.WOOD, Sources.STONE, Sources.FOOD, Sources.GOLD, Sources.SWORD, Sources.SHIELD};
    private final String boardImage;
    private final String tokenImage;
    private final String factionCardPath;

    Factions(String boardImage, String tokenImage, String factionCardPath) {
        this.boardImage = boardImage;
        this.tokenImage = tokenImage;
        this.factionCardPath = factionCardPath;
    }

    public Sources[] getSourcesInOwnSupply() {
        return switch (this) {
            case EGYPT_M, EGYPT_F -> {
                Sources[] egyptianSources = new Sources[sourcesInOwnSupply.length + 1];
                System.arraycopy(sourcesInOwnSupply, 0, egyptianSources, 0, sourcesInOwnSupply.length);
                egyptianSources[sourcesInOwnSupply.length] = Sources.EGYPT_TOKEN;
                yield egyptianSources;
            }
            default -> sourcesInOwnSupply;
        };
    }
}
