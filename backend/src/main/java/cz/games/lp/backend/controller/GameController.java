package cz.games.lp.backend.controller;

import cz.games.lp.backend.service.GameService;
import cz.games.lp.common.dto.PlayerDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/status")
    public PlayerDTO getStatus() {
        return gameService.getPlayerService().getPlayers().getFirst();
    }
}
