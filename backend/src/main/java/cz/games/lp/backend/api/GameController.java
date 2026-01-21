package cz.games.lp.backend.api;

import cz.games.lp.backend.service.agregates.GameService;
import cz.games.lp.gamecore.Player;
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
    public Player getStatus() {
        return gameService.getPlayerService().getPlayers().getFirst();
    }
}
