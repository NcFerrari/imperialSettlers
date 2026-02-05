package cz.games.lp.backend.api;

import cz.games.lp.backend.service.GameService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final GameService gameService;

    public CardController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/dealFirstCards")
    public void dealFirstCards(@RequestParam("roomID") UUID roomID) {
        gameService.dealFirstCardsToAllPlayers(roomID);
    }

    @PostMapping("/performLookoutPhase")
    public void performLookoutPhase(@RequestParam("roomID") UUID roomID) {
        gameService.performLookoutPhase(roomID);
    }
}
