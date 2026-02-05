package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.components.GameRoom;
import cz.games.lp.gamecore.components.enums.FactionTypes;
import cz.games.lp.gamecore.components.enums.RoundPhases;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

public class GameRoomActions {

    @Getter
    private final FactionActions factionActions;
    @Getter
    private final CardActions cardActions;
    private final Random random = new Random();
    private final Map<UUID, GameRoom> gameRooms = new HashMap<>();

    public GameRoomActions(FactionActions factionActions, CardActions cardActions) {
        this.factionActions = factionActions;
        this.cardActions = cardActions;
    }

    public UUID createNewGameRoom() {
        GameRoom newGameRoom = new GameRoom();
        factionActions.resetFactionSelection(newGameRoom.getRemainingFactions());
        gameRooms.put(newGameRoom.getRoomID(), newGameRoom);
        return newGameRoom.getRoomID();
    }

    public Set<UUID> getRooms() {
        return gameRooms.keySet();
    }

    public void newGame(UUID roomID) {
        GameRoom gameRoom = gameRooms.get(roomID);
        gameRoom.setRoundNumber(1);
        gameRoom.setCurrentPhase(RoundPhases.LOOKOUT);
        cardActions.createNewCardDeck(gameRoom.getCommonCardDeck());
    }

    public GameRoom getRoom(UUID roomID) {
        return gameRooms.get(roomID);
    }

    public void setFirstAndCurrentPlayer(UUID roomID) {
        GameRoom gameRoom = getRoom(roomID);
        gameRoom.setCurrentPlayerIndex(random.nextInt(gameRoom.getPlayers().size()));
        nextPlayer(roomID);
        gameRoom.setFirstPlayer(gameRoom.getCurrentPlayer());
    }

    public void nextPlayer(UUID roomID) {
        GameRoom gameRoom = getRoom(roomID);
        int index = gameRoom.getCurrentPlayerIndex();
        index++;
        if (index >= gameRoom.getPlayers().size()) {
            index = 0;
        }
        gameRoom.setCurrentPlayer(gameRoom.getPlayers().get(index));
        gameRoom.setCurrentPlayerIndex(index);
    }

    public List<FactionTypes> getRemainingFactions(UUID roomID) {
        return getRoom(roomID).getRemainingFactions();
    }

    public Map<UUID, List<String>> dealFirstCardsToAllPlayers(UUID roomID) {
        return dealFactionAndCommonCards(roomID, 2);
    }

    public Map<UUID, List<String>> performLookoutPhase(UUID roomID) {
        return dealFactionAndCommonCards(roomID, 1);
    }

    private Map<UUID, List<String>> dealFactionAndCommonCards(UUID roomID, int factionCards) {
        Map<UUID, List<String>> playerMap = new HashMap<>();
        getRoom(roomID).getPlayers().forEach(player -> {
            List<String> cardIDs = new ArrayList<>();
            IntStream.range(0, factionCards).forEach(i -> cardIDs.add(cardActions.dealFactionCards(player)));
            IntStream.range(0, 2).forEach(i -> cardIDs.add(cardActions.dealCommonCards(player, getRoom(roomID))));
            playerMap.put(player.getPlayerID(), cardIDs);
        });
        return playerMap;
    }
}
