package cz.games.lp.backend.service;

import cz.games.lp.common.dto.CardDTO;

public interface CardService {

    CardDTO dealFactionCardToCurrentPlayer();

    CardDTO dealCommonCardToCurrentPlayer();

    void dealFirstCardsToAllPlayers();
}