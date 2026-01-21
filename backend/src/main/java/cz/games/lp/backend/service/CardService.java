package cz.games.lp.backend.service;

import cz.games.lp.common.dto.CardDTO;

import java.util.Map;

public interface CardService {

    Map<String, CardDTO> getCardMap();

    void prepareNewCommonCardDeck();

    void prepareNewFactionCardDecks();

    void dealFactionCardToPlayer();

    void dealCommonCardToPlayer();

    void dealInitialCardsToPlayers();
}