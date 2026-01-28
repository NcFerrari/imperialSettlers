package cz.games.lp.backend.api;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.agregates.GamePartsServices;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.gamecore.GameManager;
import cz.games.lp.gamecore.components.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameManager gameManager;
    private final GamePartsServices gamePartsServices;
    private final CardService cardService;

    public GameController(GameManager gameManager, GamePartsServices gamePartsServices, CardService cardService) {
        this.gameManager = gameManager;
        this.gamePartsServices = gamePartsServices;
        this.cardService = cardService;
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
        return cardService.dealFactionCardToCurrentPlayer();
    }

    @PostMapping("/dealCommonCard")
    public CardDTO dealCommonCard() {
        return cardService.dealCommonCardToCurrentPlayer();
    }
}
