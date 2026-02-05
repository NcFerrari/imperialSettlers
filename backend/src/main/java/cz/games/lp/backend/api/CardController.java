package cz.games.lp.backend.api;

import cz.games.lp.backend.service.GameService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final GameService gameService;

    public CardController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/dealFirstCards")
    public ResponseEntity<@NonNull Map<UUID, List<String>>> dealFirstCards(@RequestParam("roomID") UUID roomID) {
        Map<UUID, List<String>> cardMap = gameService.dealFirstCardsToAllPlayers(roomID);
        return cardMap == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(cardMap);
    }

    @PostMapping("/performLookoutPhase")
    public ResponseEntity<@NonNull Map<UUID, List<String>>> performLookoutPhase(@RequestParam("roomID") UUID roomID) {
        Map<UUID, List<String>> cardMap = gameService.performLookoutPhase(roomID);
        return cardMap == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(cardMap);
    }
}
