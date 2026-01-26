package cz.games.lp.backend.service;

public interface CardService {

    void prepareNewCommonCardDeck();

    void prepareNewFactionCardDecks();

    void dealFactionCardToPlayer();

    void dealCommonCardToPlayer();

    void dealInitialCardsToPlayers();
}