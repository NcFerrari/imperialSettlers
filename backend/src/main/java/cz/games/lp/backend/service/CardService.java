package cz.games.lp.backend.service;

import cz.games.lp.gamecore.components.Card;

public interface CardService {

    Card dealFactionCardToCurrentPlayer();

    Card dealCommonCardToCurrentPlayer();

    void dealFirstCardsToAllPlayers();

    void generateNewFactionCardDeck(int factionCardDeckCount);
}