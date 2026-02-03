package cz.games.lp.backend.service;

import cz.games.lp.gamecore.components.Card;

import java.util.UUID;

public interface CardService {

    Card dealFactionCardToCurrentPlayer();

    Card dealCommonCardToCurrentPlayer();

    void dealFirstCardsToAllPlayers();

    void generateNewCommonCardDeck(UUID uuid);
}