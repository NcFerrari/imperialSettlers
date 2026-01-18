package cz.games.lp.backend.service;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.PlayerDTO;

import java.util.List;
import java.util.Map;

public interface CardService {

    Map<String, CardDTO> getCardMap();

    void prepareNewCommonCardDeck();

    void prepareNewFactionCardDecks(List<PlayerDTO> player);

    void dealFactionCardToPlayer(PlayerDTO player);

    void dealCommonCardToPlayer(PlayerDTO player);
}