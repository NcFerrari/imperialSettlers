package cz.games.lp.backend.controller;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.gamecore.GameData;
import cz.games.lp.gamecore.service.GameDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/new-game")
    public String newGame() {
        gameDataService.newGame();
        return "new game started";
    }

    @PutMapping("/select-faction")
    public String selectFaction(@RequestBody FactionDTO faction) {
        gameDataService.selectFaction(faction);
        return "faction set";
    }
}
