package cz.games.lp.backend.controller;

import cz.games.lp.backend.service.GameService;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/deal/faction-card")
    public ResponseEntity<@NonNull String> dealFactionCard() {
        return ResponseEntity.ok("Faction card dealt");
    }
}
