package cz.games.lp.backend.controller;

import cz.games.lp.backend.service.GameApplicationService;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameApplicationService gameApplicationService;

    public GameController(GameApplicationService gameApplicationService) {
        this.gameApplicationService = gameApplicationService;
    }

    @PostMapping("/start")
    public ResponseEntity<@NonNull String> startGame() {
        gameApplicationService.initializeGame();
        return ResponseEntity.ok("Game started successfully");
    }

    @PostMapping("/phase/production")
    public ResponseEntity<@NonNull String> performProductionPhase() {
        gameApplicationService.performProductionPhase();
        return ResponseEntity.ok("Production phase completed");
    }

    @PostMapping("/deal/faction-card")
    public ResponseEntity<@NonNull String> dealFactionCard() {
        gameApplicationService.getCardService().dealFactionCard();
        return ResponseEntity.ok("Faction card dealt");
    }
}
