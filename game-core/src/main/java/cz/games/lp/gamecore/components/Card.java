package cz.games.lp.gamecore.components;

import cz.games.lp.gamecore.components.enums.CardCategories;
import cz.games.lp.gamecore.components.enums.CardEffects;
import cz.games.lp.gamecore.components.enums.CardTypes;
import cz.games.lp.gamecore.components.enums.Colors;
import cz.games.lp.gamecore.components.enums.Conditions;
import cz.games.lp.gamecore.components.enums.Sources;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Card {

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
