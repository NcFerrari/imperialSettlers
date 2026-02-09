package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.components.Player;
import cz.games.lp.gamecore.components.enums.CardCategories;
import cz.games.lp.gamecore.components.enums.CardEffects;
import cz.games.lp.gamecore.components.enums.Conditions;
import cz.games.lp.gamecore.components.enums.RoundPhases;
import cz.games.lp.gamecore.components.enums.Sources;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record ProductionActions(GameRoomActions gameRoomActions, PlayerActions playerActions, CardActions cardActions) {

    public Map<Player, List<ProduceResult>> performProductionPhase(UUID roomID) {
        gameRoomActions.getRoom(roomID).setCurrentPhase(RoundPhases.PRODUCTION);
        return produceFactionProductionCards(roomID);
    }

    private Map<Player, List<ProduceResult>> produceFactionProductionCards(UUID roomID) {
        return gameRoomActions.getRoom(roomID).getPlayers().stream().collect(Collectors.toMap(Function.identity(), player -> producePlayerCards(player, roomID)));
    }

    private List<ProduceResult> producePlayerCards(Player player, UUID roomID) {
        return player.getBuiltLocations().get(CardCategories.FACTION_PRODUCTION).stream().map(card -> produceFromSingleCard(card, roomID, player.getPlayerID())).toList();
    }

    private ProduceResult produceFromSingleCard(Card card, UUID roomID, UUID playerID) {
        if (!card.getOrEffect().isEmpty()) {
            return new ProduceResult(card.getCardId(), getSourcesFromEffects(card.getCardEffect()), getSourcesFromEffects(card.getOrEffect()));
        }
        List<Sources> sourcesList = card.getCondition() != null ? conditionProcess(card, roomID, playerID) : getSourcesFromEffects(card.getCardEffect());
        playerActions.getPlayer(roomID, playerID).getOwnSources().merge(sourcesList.getFirst(), sourcesList.size(), Integer::sum);
        return new ProduceResult(card.getCardId(), sourcesList, null);
    }

    private List<Sources> conditionProcess(Card card, UUID roomID, UUID playerID) {
        Predicate<Card> predicate = c -> c.getColors().contains(card.getCondition().getColor());
        if (card.getCondition().equals(Conditions.HAS_SAMURAI_3_MAX)) {
            predicate = Card::isSamurai;
        }

        return cardActions.getPlayerLocations(playerActions.getPlayer(roomID, playerID))
                .stream()
                .filter(predicate)
                .limit(card.getCondition().getLimit())
                .map(c -> card.getCardEffect().getFirst().getSource())
                .toList();
    }

    private List<Sources> getSourcesFromEffects(List<CardEffects> effectList) {
        return effectList
                .stream()
                .map(CardEffects::getSource)
                .toList();
    }
}