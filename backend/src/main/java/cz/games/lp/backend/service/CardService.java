package cz.games.lp.backend.service;

import cz.games.lp.gamecore.Player;

import java.util.List;

public interface CardService {

    void prepareNewCommonCardDeck();

    void prepareNewFactionCardDecks(List<Player> player);

    void dealFactionCardToPlayer();

    void dealCommonCardToPlayer();

    void dealInitialCardsToPlayers();
}