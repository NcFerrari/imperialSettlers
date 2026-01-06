package cz.games.lp.backend.controller;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.gamecore.GameData;
import cz.games.lp.gamecore.service.GameDataService;
import cz.games.lp.gamecore.service.UserInputService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameDataService gameDataService;
    private final UserInputService userInputService;

    public GameController(GameDataService gameDataService, UserInputService userInputService) {
        this.gameDataService = gameDataService;
        this.userInputService = userInputService;
    }

    @GetMapping("/game-data")
    public GameData getGameData() {
        return gameDataService.getGameData();
    }

    @PutMapping("/select-faction")
    public String selectFaction(@RequestBody FactionDTO faction) {
        userInputService.selectFaction(faction);
        return "changed";
    }
}
