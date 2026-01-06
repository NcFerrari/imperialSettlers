package cz.games.lp.backend.controller;

import cz.games.lp.gamecore.GameData;
import cz.games.lp.gamecore.service.GameDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameDataService gameDataService;


    public GameController(GameDataService gameDataService) {
        this.gameDataService = gameDataService;
    }

    @GetMapping("/game-data")
    public GameData getGameData() {
        return gameDataService.getGameData();
    }
}
