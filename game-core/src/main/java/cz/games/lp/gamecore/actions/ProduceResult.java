package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.components.enums.Sources;

import java.util.List;

public record ProduceResult(Card card, List<Sources> source) {

}
