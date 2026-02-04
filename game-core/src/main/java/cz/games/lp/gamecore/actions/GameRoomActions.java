package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.components.GameRoom;
import cz.games.lp.gamecore.components.enums.RoundPhases;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class GameRoomActions {

    @Getter
    private final FactionActions factionActions;
    private final Map<UUID, GameRoom> gameRooms = new HashMap<>();
    private final Random random = new Random();

    public GameRoomActions(FactionActions factionActions) {
        this.factionActions = factionActions;
    }

    public void newGame(UUID uuid) {
        GameRoom gameRoom = gameRooms.get(uuid);
        gameRoom.setRoundNumber(1);
        gameRoom.setCurrentPhase(RoundPhases.LOOKOUT);
        factionActions.resetFactionSelection(gameRoom.getRemainingFactions());
    }

    public UUID createNewGameRoom() {
        UUID uuid = UUID.randomUUID();
        GameRoom newGameRoom = new GameRoom();
        factionActions.resetFactionSelection(newGameRoom.getRemainingFactions());
        gameRooms.put(uuid, newGameRoom);
        return uuid;
    }

    public GameRoom getRoom(UUID uuid) {
        return gameRooms.get(uuid);
    }

    public void setFirstAndCurrentPlayer(UUID uuid) {
        GameRoom gameRoom = gameRooms.get(uuid);
        gameRoom.setCurrentPlayerIndex(random.nextInt(gameRoom.getPlayers().size()));
        nextPlayer(gameRoom);
        gameRoom.setFirstPlayer(gameRoom.getCurrentPlayer());
    }

    public void nextPlayer(GameRoom gameRoom) {
        int index = gameRoom.getCurrentPlayerIndex();
        index++;
        if (index >= gameRoom.getPlayers().size()) {
            index = 0;
        }
        gameRoom.setCurrentPlayer(gameRoom.getPlayers().get(index));
        gameRoom.setCurrentPlayerIndex(index);
    }

//    public void actionsWhenChooseFaction(Faction faction, CardActions cardActions) {
//        getCurrentPlayer().setFaction(faction);

    /// /        removeFromChoice(faction.getFactionType());
//        getCurrentPlayer().setFactionCardDeck(new CardDeck(getCurrentPlayer().getFaction().getFactionType().getCardPrefix(), 1, cardActions));
//        getCurrentPlayer().setUpOwnSources();
//        nextPlayer();
//    }
//
//
//
//
//
//    public boolean allPlayersHaveBeenProcessed() {
//        return getCurrentPlayer().equals(getFirstPlayer());
//    }
}
