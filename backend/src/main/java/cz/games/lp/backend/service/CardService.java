package cz.games.lp.backend.service;

import cz.games.lp.gamecore.actions.CardActions;
import cz.games.lp.gamecore.components.Card;

import java.util.Map;

public interface CardService {

    Map<String, Card> cardMap();

    CardActions getCardActions();
}