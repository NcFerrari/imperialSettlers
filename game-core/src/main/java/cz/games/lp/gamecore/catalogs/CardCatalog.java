package cz.games.lp.gamecore.catalogs;

import cz.games.lp.gamecore.components.Card;

import java.util.Map;

public record CardCatalog(Map<String, Card> cardMap) {
}
