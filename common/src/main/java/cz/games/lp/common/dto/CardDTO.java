package cz.games.lp.common.dto;

import cz.games.lp.common.enums.CardCategories;
import cz.games.lp.common.enums.CardEffects;
import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.common.enums.Colors;
import cz.games.lp.common.enums.Conditions;
import cz.games.lp.common.enums.Sources;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class CardDTO {

    private CardCategories cardCategory;
    private List<CardEffects> cardEffect;
    private CardEffects cardEffectForPosition;
    private String cardId;
    private String cardName;
    private CardTypes cardType;
    private List<Colors> colors;
    private Conditions condition;
    private Sources dealSource;
    private List<CardEffects> orEffect;
    private List<Sources> sourcesForBuild;
    private List<Sources> sourcesFromDestroy;
}
