package cz.games.lp.backend.serviceimpl;

import cz.games.lp.common.enums.RoundPhases;
import cz.games.lp.common.enums.Sources;
import cz.games.lp.gamecore.GameData;
import cz.games.lp.gamecore.Supply;
import cz.games.lp.gamecore.service.GameDataService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GameDataServiceImpl implements GameDataService {

    private final GameData gameData;

    public GameDataServiceImpl() {
        List<Sources> sourcesList = List.of(Sources.WOOD, Sources.STONE, Sources.FOOD, Sources.SETTLER, Sources.SWORD, Sources.SHIELD, Sources.GOLD);
        gameData = new GameData(
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                0,
                new ArrayList<>(),
                1,
                RoundPhases.LOOKOUT,
                sourcesList.stream().collect(Collectors.toMap(Function.identity(), Supply::new))
        );
    }

    @Override
    public GameData getGameData() {
        return gameData;
    }
}
