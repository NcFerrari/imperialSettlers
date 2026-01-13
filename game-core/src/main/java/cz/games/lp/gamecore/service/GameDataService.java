package cz.games.lp.gamecore.service;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.gamecore.GameData;

public interface GameDataService {

    GameData getGameData();

    void selectFaction(FactionDTO faction);

    void newGame();

    void proceedLookoutPhase();
}
