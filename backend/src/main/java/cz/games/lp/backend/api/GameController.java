package cz.games.lp.backend.api;

import cz.games.lp.backend.service.agregates.GamePartsServices;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.gamecore.GameManager;
import cz.games.lp.gamecore.components.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameManager gameManager;
    private final GamePartsServices gamePartsServices;

    public GameController(GameManager gameManager, GamePartsServices gamePartsServices) {
        this.gameManager = gameManager;
        this.gamePartsServices = gamePartsServices;
    }

    @GetMapping("/gameStatus")
    public GameManager getGameStatus() {
        return gameManager;
    }

    @GetMapping("/playerStatus")
    public Player getPlayerStatus() {
        return gameManager.getCurrentPlayer();
    }

    @GetMapping("/playerStatus/{id}")
    public Player getPlayerStatus(@PathVariable("id") int id) {
        return gamePartsServices.getPlayerService().getPlayers().get(id);
    }

    @PostMapping("/dealFactionCard")
    public CardDTO dealFactionCard() {
//        return gameManager.getCurrentPlayer().dealFactionCard();
        return null;
    }

    @PostMapping("/dealCommonCard")
    public CardDTO dealCommonCard() {
//        return gameManager.getCurrentPlayer().dealCommonCard();
        return null;
    }
}
