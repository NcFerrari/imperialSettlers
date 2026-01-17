package cz.games.lp.backend.controller;

import cz.games.lp.backend.engine.GameServices;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.gamecore.GameData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameServices gameServices;

    public GameController(GameServices gameServices) {
        this.gameServices = gameServices;
    }

    @GetMapping("/game-data")
    public GameData getGameData() {
        return gameServices.getGameData();
    }

    @GetMapping("/new-game")
    public String newGame() {
        gameServices.getGameDataService().newGame();
        return "new game started";
    }

    @PutMapping("/select-faction")
    public String selectFaction(@RequestBody FactionDTO faction) {
        gameServices.getGameDataService().selectFaction(faction);
        return "faction set";
    }
}
