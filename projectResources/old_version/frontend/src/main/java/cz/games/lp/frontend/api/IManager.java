package cz.games.lp.frontend.api;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;

import java.util.List;

public interface IManager {

    CardDTO getCardData(String cardId);

    FactionDTO getFaction(String faction);

    List<CardDTO> getMockList();
}
