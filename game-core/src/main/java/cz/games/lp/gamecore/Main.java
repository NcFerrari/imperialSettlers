package cz.games.lp.gamecore;

import cz.games.lp.gamecore.actions.FactionActions;
import cz.games.lp.gamecore.actions.GameRoomActions;
import cz.games.lp.gamecore.actions.PlayerActions;
import cz.games.lp.gamecore.components.enums.FactionTypes;

import java.util.UUID;

public class Main {

    private final FactionActions factionActions = new FactionActions();
    private final GameRoomActions gameRoomActions = new GameRoomActions(factionActions);
    private final PlayerActions playerActions = new PlayerActions(gameRoomActions);

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        initGame();
    }

    private void initGame() {
        UUID uuid = gameRoomActions.createNewGameRoom();
        playerActions.addPlayers(uuid, 1);
        playerActions.initCurrentPlayer(uuid, FactionTypes.BARBARIAN_M);
        gameRoomActions.newGame(uuid);
        playerActions.newGameForPlayers(uuid);
    }
}
