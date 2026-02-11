package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.components.Player;
import cz.games.lp.gamecore.components.enums.CardCategories;
import cz.games.lp.gamecore.components.enums.CardEffects;
import cz.games.lp.gamecore.components.enums.Conditions;
import cz.games.lp.gamecore.components.enums.RoundPhases;
import cz.games.lp.gamecore.components.enums.Sources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record ProductionActions(GameRoomActions gameRoomActions, PlayerActions playerActions, CardActions cardActions) {

    public Map<UUID, List<ProduceResult>> performProductionPhase(UUID roomID) {
        gameRoomActions.getRoom(roomID).setCurrentPhase(RoundPhases.PRODUCTION);
        return produceFactionProductionCards(roomID);
    }

    private Map<UUID, List<ProduceResult>> produceFactionProductionCards(UUID roomID) {
        return gameRoomActions.getRoom(roomID).getPlayers().stream().collect(Collectors.toMap(Player::getPlayerID, player -> producePlayerCards(player, roomID)));
    }

    private List<ProduceResult> producePlayerCards(Player player, UUID roomID) {
        return player.getBuiltLocations().get(CardCategories.FACTION_PRODUCTION).stream().map(card -> produceFromSingleCard(card, roomID, player.getPlayerID())).toList();
    }

    private ProduceResult produceFromSingleCard(Card card, UUID roomID, UUID playerID) {
        List<Sources> sourcesList = card.getCondition() != null ? conditionProcess(card, roomID, playerID) : getSourcesFromEffects(card.getCardEffect());
        if (card.getOrEffect().isEmpty()) {
            sourcesList
                    .stream()
                    .filter(source -> !List.of(Sources.CARD, Sources.LOCATION).contains(source))
                    .forEach(source -> {
                        switch (source) {
                            case Sources.FACTION_CARD ->
                                    cardActions.dealFactionCard(playerActions.getPlayer(roomID, playerID));
                            case Sources.COMMON_CARD ->
                                    cardActions.dealCommonCard(playerActions.getPlayer(roomID, playerID), gameRoomActions.getRoom(roomID));
                            case Sources.VICTORY_POINT ->
                                    playerActions.addVictoryPointToPlayer(playerActions.getPlayer(roomID, playerID));
                            default ->
                                    playerActions.getPlayer(roomID, playerID).getOwnSources().merge(source, 1, Integer::sum);
                        }
                    });
        }
        return new ProduceResult(card.getCardId(), sourcesList, getSourcesFromEffects(card.getOrEffect()));
    }

    private List<Sources> conditionProcess(Card card, UUID roomID, UUID playerID) {
        Predicate<Card> predicate = c -> c.getColors().contains(card.getCondition().getColor());
        if (card.getCondition().equals(Conditions.HAS_SAMURAI_3_MAX)) {
            predicate = Card::isSamurai;
        }

        return new ArrayList<>(cardActions.getPlayerLocations(playerActions.getPlayer(roomID, playerID))
                .stream()
                .filter(predicate)
                .limit(card.getCondition().getLimit())
                .map(c -> card.getCardEffect().getFirst().getSource())
                .toList());
    }

    private List<Sources> getSourcesFromEffects(List<CardEffects> effectList) {
        return new ArrayList<>(effectList
                .stream()
                .map(CardEffects::getSource)
                .toList());
    }
}