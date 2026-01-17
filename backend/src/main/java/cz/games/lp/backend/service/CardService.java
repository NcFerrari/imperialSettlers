package cz.games.lp.backend.service;

import cz.games.lp.common.dto.CardDTO;

import java.util.Map;

public interface CardService {

    Map<String, CardDTO> getCardMap();

}