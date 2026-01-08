package cz.games.lp.backend.controller;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.gamecore.GameData;
import cz.games.lp.gamecore.service.GameDataService;
import cz.games.lp.gamecore.service.GameManagerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameDataService gameDataService;
    private final GameManagerService gameManagerService;

    public GameController(GameDataService gameDataService, GameManagerService gameManagerService) {
        this.gameDataService = gameDataService;
        this.gameManagerService = gameManagerService;
    }

    @GetMapping("/game-data")
    public GameData getGameData() {
        return gameDataService.getGameData();
    }

    @GetMapping("/new-game")
    public String newGame() {
        gameManagerService.newGame();
        return "OK";
    }

    @PutMapping("/select-faction")
    public String selectFaction(@RequestBody FactionDTO faction) {
        gameDataService.selectFaction(faction);
        return "changed";
    }
}
