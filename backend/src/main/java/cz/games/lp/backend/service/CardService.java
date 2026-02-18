package cz.games.lp.backend.service;

import cz.games.lp.gamecore.actions.CardActions;
import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.components.GameRoom;
import cz.games.lp.gamecore.components.Player;

import java.util.Map;

public interface CardService {

    Map<String, Card> cardMap();

    CardActions getCardActions();

    Card getNewPlayerCard(Player player, int cardNumber);

    String dealCardToPlayer(Player player, int cardNumber, boolean shuffleRestOfCards);

    String dealFactionCardToPlayer(Player player);

    String dealCommonCardToPlayer(Player player, GameRoom gameRoom);
}