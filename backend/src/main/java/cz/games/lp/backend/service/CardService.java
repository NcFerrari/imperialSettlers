package cz.games.lp.backend.service;

public interface CardService {

    void dealFactionCardToCurrentPlayer();

    void dealCommonCardToCurrentPlayer();

    void dealFirstCardsToAllPlayers();
}