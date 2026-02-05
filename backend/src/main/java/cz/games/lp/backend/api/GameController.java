package cz.games.lp.backend.api;

import cz.games.lp.backend.service.GameService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.gamecore.components.GameRoom;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;
    private final PlayerService playerService;

    public GameController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @PostMapping("/createNewRoom")
    public UUID createNewGameRoom() {
        return gameService.createNewGameRoom();
    }

    @GetMapping("/rooms")
    public Set<UUID> getExistingRooms() {
        return gameService.getRooms();
    }

    @GetMapping("/getRoom")
    public ResponseEntity<@NonNull GameRoom> getGameRoom(@RequestParam("roomID") UUID roomID) {
        GameRoom gameRoom = gameService.getRoom(roomID);
        return gameRoom == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(gameRoom);
    }

    @PostMapping("/newGame")
    public void newGame(@RequestParam("roomID") UUID roomID) {
        gameService.newGame(roomID);
        playerService.newGameForAllPlayers(roomID);
    }
}
