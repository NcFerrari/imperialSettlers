package cz.games.lp.backend.api;

import cz.games.lp.backend.service.FactionService;
import cz.games.lp.backend.service.GameService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.gamecore.components.Player;
import cz.games.lp.gamecore.components.enums.FactionTypes;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private final PlayerService playerService;
    private final FactionService factionService;
    private final GameService gameService;

    public PlayerController(PlayerService playerService, FactionService factionService, GameService gameService) {
        this.playerService = playerService;
        this.factionService = factionService;
        this.gameService = gameService;
    }

    @PostMapping("/addPlayers")
    public List<UUID> addPlayerToRoom(@RequestParam("roomID") UUID roomID, @RequestParam("playerCount") int playerCount) {
        return playerService.addPlayers(roomID, playerCount);
    }

    @GetMapping("/getPlayers")
    public List<Player> getPlayers(@RequestParam("roomID") UUID roomID) {
        return playerService.getPlayers(roomID);
    }

    @GetMapping("/getPlayer")
    public ResponseEntity<@NonNull Player> getPlayer(@RequestParam("roomID") UUID roomID, @RequestParam("playerID") UUID playerID) {
        Player player = playerService.getPlayer(roomID, playerID);
        return player == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(player);
    }

    @PutMapping("/selectFactionForPlayer")
    public void selectFactionForPlayer(@RequestParam("roomID") UUID roomID, @RequestParam("playerID") UUID playerID, @RequestParam("factionType") FactionTypes factionType) {
        playerService.resetPlayerForSelectingFaction(roomID, playerID);
        playerService.initPlayerAndUpdateGameRoom(roomID, playerID, factionType);
        factionService.removeFromChoice(gameService.getRemainingFactions(roomID), factionType);
    }
}