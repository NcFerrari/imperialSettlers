package cz.games.lp.backend.api;

import cz.games.lp.backend.service.agregates.GamePartsServices;
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

    private final GamePartsServices gamePartsServices;

    public GameController(GamePartsServices gamePartsServices) {
        this.gamePartsServices = gamePartsServices;
    }

    @PostMapping("/createNewRoom")
    public UUID createNewGameRoom() {
        return gamePartsServices.getGameService().createNewGameRoom();
    }

    @GetMapping("/rooms")
    public Set<UUID> getExistingRooms() {
        return gamePartsServices.getGameService().getRooms();
    }

    @GetMapping("/getRoom")
    public ResponseEntity<@NonNull GameRoom> getGameRoom(@RequestParam("roomID") UUID roomID) {
        GameRoom gameRoom = gamePartsServices.getGameService().getRoom(roomID);
        return gameRoom == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(gameRoom);
    }
}
