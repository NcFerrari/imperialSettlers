package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.components.Player;
import cz.games.lp.gamecore.components.enums.CardCategories;
import cz.games.lp.gamecore.components.enums.Conditions;
import cz.games.lp.gamecore.components.enums.RoundPhases;
import cz.games.lp.gamecore.components.enums.Sources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record ProductionActions(GameRoomActions gameRoomActions, PlayerActions playerActions, CardActions cardActions) {

    public Map<Player, List<ProduceResult>> performProductionPhase(UUID roomID) {
        gameRoomActions.getRoom(roomID).setCurrentPhase(RoundPhases.PRODUCTION);
        return produceFactionProductionCards(roomID);
    }

    private Map<Player, List<ProduceResult>> produceFactionProductionCards(UUID roomID) {
        return gameRoomActions.getRoom(roomID).getPlayers().stream().collect(Collectors.toMap(Function.identity(), player -> producePlayerCards(player, roomID)));
    }

    private List<ProduceResult> producePlayerCards(Player player, UUID roomID) {
        return player.getBuiltLocations().get(CardCategories.FACTION_PRODUCTION).stream().map(card -> analyzeCardProduction(card, roomID, player.getPlayerID())).toList();
    }

    private ProduceResult analyzeCardProduction(Card card, UUID roomID, UUID playerID) {
        if (!card.getOrEffect().isEmpty()) {
            return null;
        } else if (card.getCondition() != null) {
            return produceCardWithCondition(card, roomID, playerID);
        }
        return produceCardEffect(card, roomID, playerID);
    }

    private ProduceResult produceCardWithCondition(Card card, UUID roomID, UUID playerID) {
        List<Card> locations = cardActions.getPlayerLocations(playerActions.getPlayer(roomID, playerID));
        Predicate<Card> predicate = c -> c.getColors().contains(card.getCondition().getColor());
        if (card.getCondition().equals(Conditions.HAS_SAMURAI_3_MAX)) {
            predicate = Card::isSamurai;
        }
        int countCardsForCondition = (int) locations
                .stream()
                .filter(predicate)
                .limit(card.getCondition().getLimit())
                .count();
        return new ProduceResult(card, IntStream.range(0, countCardsForCondition).mapToObj(i -> card.getCardEffect().getFirst().getSource()).toList());
    }

    private ProduceResult produceCardEffect(Card card, UUID roomID, UUID playerID) {
        List<Sources> sourcesList = new ArrayList<>();
        card.getCardEffect().forEach(effect -> {
            playerActions.getPlayer(roomID, playerID).getOwnSources().merge(effect.getSource(), 1, Integer::sum);
            sourcesList.add(effect.getSource());
        });
        return new ProduceResult(card, sourcesList);
    }
}