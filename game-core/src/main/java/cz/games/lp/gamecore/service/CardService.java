package cz.games.lp.gamecore.service;

import cz.games.lp.common.dto.CardDTO;

import java.util.Map;

public interface CardService {

    Map<String, CardDTO> getCardMap();

    void deal4Cards();

    void dealFactionCard();

    void dealCommonCard();
}
