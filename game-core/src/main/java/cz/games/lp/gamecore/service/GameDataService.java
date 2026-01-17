package cz.games.lp.gamecore.service;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.gamecore.GameData;

import java.util.concurrent.CompletableFuture;

public interface GameDataService {

    CompletableFuture<String> prepareData();

    GameData getGameData();

    void selectFaction(FactionDTO faction);

    void newGame();
}
