package cz.games.lp.backend.json_object;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CardJSON {

    private List<String> cardEffect = new ArrayList<>();
    private String cardEffectForPosition;
    private String cardId;
    private String cardName;
    private String cardType;
    private List<String> colors = new ArrayList<>();
    private String condition;
    private String dealSource;
    private List<String> orEffect = new ArrayList<>();
    private List<String> sourcesForBuild = new ArrayList<>();
    private List<String> sourcesFromDestroy = new ArrayList<>();
}
