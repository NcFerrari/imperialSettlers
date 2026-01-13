package cz.games.lp.backend.serviceimpl;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.gamecore.GameData;
import cz.games.lp.gamecore.service.GameDataService;
import org.springframework.stereotype.Service;

@Service
public class GameDataServiceImpl implements GameDataService {

    private final GameData gameData;

    public GameDataServiceImpl() {
        gameData = new GameData();
    }

    @Override
    public GameData getGameData() {
        return gameData;
    }

    @Override
    public void selectFaction(FactionDTO faction) {
        gameData.setSelectedFaction(faction);
    }

    @Override
    public void newGame() {
        gameData.newGame();
    }

    @Override
    public void proceedLookoutPhase() {

    }
}
