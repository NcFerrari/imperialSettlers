package cz.games.lp.backend.api;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.agregates.GamePartsServices;
import cz.games.lp.gamecore.components.GameRoom;
import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.components.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GamePartsServices gamePartsServices;
    private final CardService cardService;

    public GameController(GamePartsServices gamePartsServices, CardService cardService) {
        this.gamePartsServices = gamePartsServices;
        this.cardService = cardService;
    }

    @PutMapping("/createNewRoom")
    public UUID createNewRoom() {
        return gamePartsServices.getGameService().createNewGameRoom();
    }

    @PostMapping("/setPlayerCount/{uuid}/{countOfPlayers}")
    public void setPlayerCount(@PathVariable("uuid") UUID uuid, @PathVariable("countOfPlayers") int numberOfPlayers) {
        gamePartsServices.getPlayerService().initializePlayers(uuid, numberOfPlayers);
    }

    @GetMapping("/getPlayerCount")
    public String check() {
        StringBuilder s = new StringBuilder();
        gamePartsServices.getGameService().getGameRooms().forEach((uuid, gameRoom) -> s.append(uuid).append(": ").append(gameRoom.getPlayers().size()).append("\n"));
        return s.toString();
    }

    @GetMapping("/getRooms")
    public Set<UUID> getRooms() {
        return gamePartsServices.getGameService().getGameRooms().keySet();
    }

    @GetMapping("/getRoom/{UUID}")
    public GameRoom getRoom(@PathVariable("UUID") UUID roomUUID) {
        return gamePartsServices.getGameService().getGameRoom(roomUUID);
    }

    @GetMapping("/playerStatus/{id}")
    public Player getPlayerStatus(@PathVariable("id") int id) {
        return gamePartsServices.getPlayerService().getPlayers().get(id);
    }

    @PostMapping("/dealFactionCard")
    public Card dealFactionCard() {
        return cardService.dealFactionCardToCurrentPlayer();
    }

    @PostMapping("/dealCommonCard")
    public Card dealCommonCard() {
        return cardService.dealCommonCardToCurrentPlayer();
    }
}
