package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.components.Player;
import cz.games.lp.gamecore.components.enums.CardTypes;
import cz.games.lp.gamecore.components.enums.FactionTypes;
import cz.games.lp.gamecore.components.enums.Sources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record PlayerActions(GameRoomActions gameRoomActions) {

    private static final int MAXIMUM_PLAYERS = 4;
    private static final Sources[] PLAYERS_BASIC_SOURCES = new Sources[]{
            Sources.SETTLER,
            Sources.WOOD,
            Sources.STONE,
            Sources.FOOD,
            Sources.COIN,
            Sources.SWORD,
            Sources.SHIELD
    };

    public List<UUID> addPlayers(UUID roomID, int playerCount) {
        if (!gameRoomActions.getRoom(roomID).getPlayers().isEmpty()) {
            return Collections.emptyList();
        }
        IntStream.range(0, Math.min(MAXIMUM_PLAYERS, playerCount)).forEach(i -> addPlayer(roomID));
        gameRoomActions.setFirstAndCurrentPlayer(roomID);
        return gameRoomActions.getRoom(roomID).getPlayers().stream().map(Player::getPlayerID).toList();
    }

    public UUID addPlayer(UUID roomID) {
        Player player = new Player();
        gameRoomActions.getRoom(roomID).getPlayers().add(player);
        if (gameRoomActions.getRoom(roomID).getPlayers().size() == 1) {
            gameRoomActions.setFirstAndCurrentPlayer(roomID);
        }
        return player.getPlayerID();
    }

    public List<Player> getPlayers(UUID roomID) {
        return gameRoomActions.getRoom(roomID).getPlayers();
    }

    public Player getPlayer(UUID roomID, UUID playerID) {
        Optional<Player> possiblePlayer = gameRoomActions.getRoom(roomID).getPlayers().stream().filter(player -> playerID.equals(player.getPlayerID())).findFirst();
        return possiblePlayer.orElse(null);
    }

    public void initPlayerAndUpdateGameRoom(UUID roomID, UUID playerID, FactionTypes factionType) {
        Player player = getPlayer(roomID, playerID);
        if (player == null) {
            return;
        }
        player.setFaction(gameRoomActions.getFactionActions().getFactionCatalog().factionMap().get(factionType));
        gameRoomActions.getFactionActions().setFactionToCardDeck(player.getFactionCardDeck(), factionType);
        player.getFactionCardDeck().getCards().clear();
        setUpOwnSources(roomID, playerID);
        gameRoomActions.getFactionActions().removeFromChoice(gameRoomActions.getRemainingFactions(roomID), factionType);
    }

    private void setUpOwnSources(UUID roomID, UUID playerID) {
        Player player = getPlayer(roomID, playerID);
        if (player == null || player.getFaction() == null) {
            return;
        }
        player.getOwnSources().clear();
        Stream.of(PLAYERS_BASIC_SOURCES).forEach(source -> player.getOwnSources().put(source, 0));
        if (EnumSet.of(FactionTypes.EGYPT_F, FactionTypes.EGYPT_M).contains(player.getFaction().getFactionType())) {
            player.getOwnSources().put(Sources.EGYPT_TOKEN, 0);
        }
    }

    public void newGameForPlayers(UUID roomID) {
        gameRoomActions.getRoom(roomID).getPlayers().forEach(this::newGame);
    }

    private void newGame(Player player) {
        resetStats(player);
        gameRoomActions.getCardActions().createNewCardDeck(player.getFactionCardDeck());
    }

    public void resetAllPlayersForSelectingFaction(UUID roomID) {
        getPlayers(roomID).forEach(this::resetForSelectingFaction);
    }

    public void resetForSelectingFaction(Player player) {
        resetStats(player);
        player.getFactionCardDeck().getCards().clear();
        player.getFactionCardDeck().setCardPrefix(CardTypes.FACTION);
        player.setFaction(null);
    }

    private void resetStats(Player player) {
        player.getOwnSources().replaceAll((sources, value) -> 0);
        player.getCardsInHand().clear();
        player.getBuiltLocations().replaceAll((key, value) -> new ArrayList<>());
        player.getDeals().clear();
        player.setVictoryPoints(0);
    }

    public void addVictoryPointToPlayer(Player player) {
        player.setVictoryPoints(player.getVictoryPoints() + 1);
    }
}
