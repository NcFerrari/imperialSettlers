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
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record ProductionActions(GameRoomActions gameRoomActions, PlayerActions playerActions, CardActions cardActions,
                                SourceActions sourceActions) {

    public Map<UUID, List<ProduceChoice>> produceFactionCards(UUID roomID) {
        gameRoomActions.getRoom(roomID).setCurrentPhase(RoundPhases.PRODUCTION);
        return gameRoomActions.getRoom(roomID).getPlayers()
                .stream()
                .collect(Collectors.toMap(
                        Player::getPlayerID,
                        player -> player.getBuiltLocations().get(CardCategories.FACTION_PRODUCTION).stream().map(card -> produceFromSingleCard(card, roomID, player.getPlayerID())).toList()
                ));
    }

    public Map<UUID, List<ProduceChoice>> produceCommonCards(UUID roomID) {
        return gameRoomActions.getRoom(roomID).getPlayers()
                .stream()
                .collect(Collectors.toMap(
                        Player::getPlayerID,
                        player -> player.getBuiltLocations().get(CardCategories.COMMON_PRODUCTION).stream().map(card -> produceFromSingleCard(card, roomID, player.getPlayerID())).toList()
                ));
    }

    private ProduceChoice produceFromSingleCard(Card card, UUID roomID, UUID playerID) {
        List<Sources> sourcesList = card.getCondition() != null ? conditionProcess(card, roomID, playerID) : getSourcesFromEffects(card.getCardEffect());
        Player player = playerActions.getPlayer(roomID, playerID);
        if (card.getOrEffect().isEmpty()) {
            sourcesList
                    .stream()
                    .filter(source -> !Objects.equals(Sources.CARD, source))
                    .forEach(source -> {
                        switch (source) {
                            case Sources.VICTORY_POINT -> playerActions.addVictoryPointToPlayer(player);
                            case Sources.COMMON_CARD ->
                                    cardActions.dealCommonCard(player, gameRoomActions.getRoom(roomID));
                            case Sources.FACTION_CARD -> cardActions.dealFactionCard(player);
                            default -> player.getOwnSources().merge(source, 1, Integer::sum);
                        }
                    });
        }
        return new ProduceChoice(card.getCardId(), sourcesList, getSourcesFromEffects(card.getOrEffect()), null);
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

    public Map<UUID, List<ProduceChoice>> produceDeals(UUID roomID) {
        return gameRoomActions.getRoom(roomID).getPlayers().stream().collect(Collectors.toMap(Player::getPlayerID, this::produceDeals));
    }

    private List<ProduceChoice> produceDeals(Player player) {
        player.getDeals()
                .stream()
                .filter(card -> !Sources.CARD.equals(card.getDealSource()))
                .forEach(card -> {
                    if (Sources.VICTORY_POINT.equals(card.getDealSource())) {
                        playerActions.addVictoryPointToPlayer(player);
                    } else {
                        player.getOwnSources().merge(card.getDealSource(), 1, Integer::sum);
                    }
                });
        return player.getDeals().stream().map(card -> new ProduceChoice(card.getCardId(), null, null, card.getDealSource())).toList();
    }

    public Map<UUID, ProduceChoice> produceFactionBoard(UUID roomID) {
        gameRoomActions.getRoom(roomID).getPlayers().forEach(player -> player.getFaction().getFactionProduction().forEach(source -> player.getOwnSources().merge(source, 1, Integer::sum)));
        return gameRoomActions.getRoom(roomID).getPlayers()
                .stream()
                .collect(Collectors.toMap(
                        Player::getPlayerID,
                        player -> new ProduceChoice(null, player.getFaction().getFactionProduction(), null, null)
                ));
    }
}