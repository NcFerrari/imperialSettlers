package cz.games.lp.backend.serviceimpl;

import cz.games.lp.common.enums.Sources;
import cz.games.lp.gamecore.GameData;
import cz.games.lp.gamecore.Supply;
import cz.games.lp.gamecore.service.GameDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameDataServiceImpl implements GameDataService {

    private final GameData gameData;

    public GameDataServiceImpl() {
        List<Sources> sourcesList = List.of(Sources.WOOD, Sources.STONE, Sources.FOOD, Sources.SETTLER, Sources.SWORD, Sources.SHIELD, Sources.GOLD);
        gameData = new GameData();
        sourcesList.forEach(source -> gameData.getOwnSupplies().put(source, new Supply(source)));
    }

    @Override
    public GameData getGameData() {
        return gameData;
    }
}
