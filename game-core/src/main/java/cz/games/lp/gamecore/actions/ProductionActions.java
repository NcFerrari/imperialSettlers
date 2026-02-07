package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.components.enums.CardCategories;
import cz.games.lp.gamecore.components.enums.RoundPhases;

import java.util.UUID;

public record ProductionActions(GameRoomActions gameRoomActions) {

    public void performProductionPhase(UUID roomID) {
        gameRoomActions.getRoom(roomID).setCurrentPhase(RoundPhases.PRODUCTION);
        produceFactionProductionCards(roomID);
    }

    private void produceFactionProductionCards(UUID roomID) {
        gameRoomActions.getRoom(roomID).getPlayers().forEach(player -> player.getBuiltLocations().get(CardCategories.FACTION_PRODUCTION).forEach(this::produceCard));
    }

    private void produceCard(Card card) {

    }
}