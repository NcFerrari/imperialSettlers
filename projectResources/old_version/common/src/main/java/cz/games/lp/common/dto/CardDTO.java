package cz.games.lp.common.dto;

import cz.games.lp.common.enums.CardEffects;
import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.common.enums.Colors;
import cz.games.lp.common.enums.Conditions;
import cz.games.lp.common.enums.Sources;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CardDTO {

    private List<CardEffects> cardEffect = new ArrayList<>();
    private CardEffects cardEffectForPosition;
    private String cardId;
    private String cardName;
    private CardTypes cardType;
    private List<Colors> colors = new ArrayList<>();
    private Conditions condition;
    private Sources dealSource;
    private List<CardEffects> orEffect = new ArrayList<>();
    private List<Sources> sourcesForBuild = new ArrayList<>();
    private List<Sources> sourcesFromDestroy = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("%s[ID:%s, TYPE:%S]", cardName, cardId, cardType);
    }
}
