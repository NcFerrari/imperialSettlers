package cz.games.lp.backend.api;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.gamecore.GameManager;
import cz.games.lp.gamecore.components.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameManager gameManager;

    public GameController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @GetMapping("/gameStatus")
    public GameManager getGameStatus() {
        return gameManager;
    }

    @GetMapping("/playerStatus")
    public Player getPlayerStatus() {
        return gameManager.getCurrentPlayer();
    }

    @PostMapping("/dealFactionCard")
    public CardDTO dealFactionCard() {
        return gameManager.getCurrentPlayer().dealFactionCard();
    }

    @PostMapping("/dealCommonCard")
    public CardDTO dealCommonCard() {
        return gameManager.getCurrentPlayer().dealCommonCard();
    }
}
