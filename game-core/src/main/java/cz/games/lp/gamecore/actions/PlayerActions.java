package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.components.CardDeck;
import cz.games.lp.gamecore.components.Player;
import cz.games.lp.gamecore.components.enums.FactionTypes;
import cz.games.lp.gamecore.components.enums.Sources;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public record PlayerActions(GameRoomActions gameRoomActions) {

    private static final Sources[] PLAYERS_BASIC_SOURCES = new Sources[]{
            Sources.SETTLER,
            Sources.WOOD,
            Sources.STONE,
            Sources.FOOD,
            Sources.COIN,
            Sources.SWORD,
            Sources.SHIELD
    };

    public void addPlayers(UUID uuid, int countOfPlayers) {
        IntStream.range(0, countOfPlayers).forEach(i -> gameRoomActions.getRoom(uuid).getPlayers().add(new Player()));
        gameRoomActions.setFirstAndCurrentPlayer(uuid);
    }

    public void initCurrentPlayer(UUID uuid, FactionTypes factionTypes) {
        Player currentPlayer = gameRoomActions.getRoom(uuid).getCurrentPlayer();
        currentPlayer.setFactionCardDeck(new CardDeck(factionTypes.getCardPrefix(), CardDeck.FACTION_CARD_DECK_COUNT));
        currentPlayer.setFaction(gameRoomActions.getFactionActions().getFactionCatalog().factionMap().get(factionTypes));
        currentPlayer.setUpOwnSources(PLAYERS_BASIC_SOURCES);
        gameRoomActions.getFactionActions().removeFromChoice(gameRoomActions.getRoom(uuid).getRemainingFactions(), factionTypes);
    }

    public void newGameForPlayers(UUID uuid) {
        gameRoomActions.getRoom(uuid).getPlayers().forEach(Player::newGame);
    }

    public List<Player> getPlayers(UUID uuid) {
        return gameRoomActions.getRoom(uuid).getPlayers();
    }
}
