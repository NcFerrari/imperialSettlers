package cz.games.lp.frontend.game_actions;

import cz.games.lp.common.enums.CardEffects;
import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.common.enums.Conditions;
import cz.games.lp.common.enums.Sources;
import cz.games.lp.frontend.components.Choice;
import cz.games.lp.frontend.components.ImageNode;
import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.enums.ProductionBlocks;
import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class ProductionActions {

    private final CommonModel model;
    private final AtomicLong stopTime = new AtomicLong();
    private final AtomicInteger counter = new AtomicInteger();
    private Card selectedCard;
    private ProductionBlocks block;
    private double moveFactionBy;
    private double factionHValue;
    private double moveDealBy;
    private double dealVvalue;
    private double moveCommonBy;
    private double commonHvalue;
    private ObservableList<Node> factionCards;
    private ObservableList<Node> deals;
    private ObservableList<Node> commonCards;
    private double delay;
    private HighlightMessenger data;

    public ProductionActions(CommonModel model) {
        this.model = model;
        selectedCard = new Card(model);
    }

    public Consumer<Long> proceedProduction() {
        updateData();
        return time -> {
            delay = model.getUIConfig().getAnimationSpeed();
            if (time - stopTime.get() < delay * 1_000_000L ||
                    model.getActionManager().isPointAnimationIsRunning() ||
                    model.getChoiceDialog().isShowing() ||
                    model.isSequentialRunning()) {
                return;
            }
            switch (block) {
                case ProductionBlocks.FACTIONS -> {
                    data = new HighlightMessenger(factionCards, model.getFactionCards().get(CardTypes.PRODUCTION), factionHValue, 0, ProductionBlocks.DEALS, counter.decrementAndGet(), -1, () -> processCard(selectedCard), time);
                    highlightCard(data);
                    factionHValue += moveFactionBy;
                }
                case ProductionBlocks.DEALS -> {
                    data = new HighlightMessenger(deals, model.getDeals().getScrollPane(), 0, dealVvalue, ProductionBlocks.FACTION_BOARD, counter.incrementAndGet(), deals.size(), () -> proceedDeal(selectedCard), time);
                    highlightCard(data);
                    dealVvalue += moveDealBy;
                }
                case ProductionBlocks.FACTION_BOARD -> highlightFactionBoard();
                case ProductionBlocks.COMMONS -> {
                    model.getFactionBoard().deselect();
                    data = new HighlightMessenger(commonCards, model.getCommonCards().get(CardTypes.PRODUCTION), commonHvalue, 0, ProductionBlocks.DEFAULT, counter.getAndIncrement(), commonCards.size(), () -> processCard(selectedCard), time);
                    highlightCard(data);
                    commonHvalue += moveCommonBy;
                }
                default -> {
                    selectedCard.deselect();
                    model.getActionManager().setAnimationRunning(false);
                    model.getActionManager().stop();
                    model.getRoundPhases().enableButtonForFollowingPhase();
                }
            }
        };
    }

    public void processCard(Card selectedCard) {
        this.selectedCard = selectedCard;
        if (selectedCard.getCardData().getCondition() != null) {
            processCondition(selectedCard.getCardData().getCondition());
        } else if (!selectedCard.getCardData().getOrEffect().isEmpty()) {
            Choice firstChoice = getSourceChoice(selectedCard.getCardData().getCardEffect().getFirst());
            Choice secondChoice = getSourceChoice(selectedCard.getCardData().getOrEffect().getFirst());
            model.getChoiceDialog().showDialog(List.of(firstChoice, secondChoice));
        } else if (CardEffects.ANOTHER_PRODUCTION.equals(selectedCard.getCardData().getCardEffect().getFirst())) {
            List<Choice> choices = FXCollections.observableArrayList();
            ((HBox) model.getFactionCards().get(CardTypes.PRODUCTION).getContent()).getChildren().reversed().forEach(node -> {
                Card card = new Card(model.getGameData().getSelectedFaction().getFactionCardPath() + "/" + ((Card) node).getCardId(), ((Card) node).getCardId(), model);
                choices.add(new Choice(card, () -> processCard(((Card) node))));
            });
            ((HBox) model.getCommonCards().get(CardTypes.PRODUCTION).getContent()).getChildren().forEach(node -> {
                Card card = new Card(Texts.COMMON.get() + "/" + ((Card) node).getCardId(), ((Card) node).getCardId(), model);
                choices.add(new Choice(card, () -> processCard(((Card) node))));
            });
            model.getChoiceDialog().showDialog(choices);
        } else {
            model.getActionManager().addSourceWithEffect(selectedCard.getCardData().getCardEffect().stream().map(CardEffects::getSource).toList(), selectedCard);
        }
    }

    private void proceedDeal(Card selectedCard) {
        model.getActionManager().addSourceWithEffect(List.of(selectedCard.getCardData().getDealSource()), model.getDeals());
    }

    private void processFactionBoard() {
        model.getActionManager().addSourceWithEffect(model.getFactionBoard().getFactionData().getFactionProduction(), model.getFactionBoard());
    }

    private void updateData() {
        model.getFactionCards().get(CardTypes.PRODUCTION).setHvalue(0);
        model.getDeals().getScrollPane().setVvalue(0);
        model.getCommonCards().get(CardTypes.PRODUCTION).setHvalue(0);
        factionCards = ((HBox) model.getFactionCards().get(CardTypes.PRODUCTION).getContent()).getChildren();
        deals = model.getDeals().getCards();
        commonCards = ((HBox) model.getCommonCards().get(CardTypes.PRODUCTION).getContent()).getChildren();
        moveFactionBy = 1.0 / (factionCards.size() - 1);
        factionHValue = 0;
        moveDealBy = 1.0 / (deals.size() - 1);
        dealVvalue = 0;
        moveCommonBy = 1.0 / (commonCards.size() - 1);
        commonHvalue = 0;
        counter.set(factionCards.size());
        block = ProductionBlocks.FACTIONS;
    }

    private void highlightCard(HighlightMessenger data) {
        if (data.index == data.finish) {
            block = data.nextProductionBlock;
            return;
        }
        stopTime.set(data.time);
        selectedCard.deselect();
        selectedCard = (Card) data.list.get(data.index);
        selectedCard.select();
        smoothScroll(data.scrollPane, data.hValue, data.vValue);
        data.processProduction.run();
    }

    private void highlightFactionBoard() {
        selectedCard.deselect();
        model.getFactionBoard().select();
        processFactionBoard();
        counter.set(0);
        block = ProductionBlocks.COMMONS;
    }

    private void smoothScroll(ScrollPane scrollPane, double targetHvalue, double targetVvalue) {
        Timeline timeline = new Timeline();

        KeyValue kv = new KeyValue(scrollPane.hvalueProperty(), targetHvalue, Interpolator.EASE_BOTH);
        if (targetHvalue == 0) {
            kv = new KeyValue(scrollPane.vvalueProperty(), targetVvalue, Interpolator.EASE_BOTH);
        }
        KeyFrame kf = new KeyFrame(Duration.millis(300), kv);

        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public void processCondition(Conditions condition) {
        List<Sources> list;
        switch (condition) {
            case Conditions.FACTION_CARD_2 -> {
                String path = model.getGameData().getSelectedFaction().getFactionCardPath();
                model.getChoiceDialog().showDialog(List.of(getFactionCardChoice(path, 0), getFactionCardChoice(path, 1)));
            }
            case Conditions.SAMURAI_3 -> {
                int count = CardsOperation.getCardsCountWithCondition(model, Card::hasSamurai, Conditions.SAMURAI_3.getLimit());
                list = IntStream.range(0, count).mapToObj(i -> Sources.SCORE_POINT).toList();
                model.getActionManager().addSourceWithEffect(list, selectedCard);
            }
            default -> {
                int count = CardsOperation.getCardsCountWithCondition(model, card -> card.getCardData().getColors().contains(condition.getColor()), selectedCard.getCardData().getCondition().getLimit());
                list = IntStream.range(0, count).mapToObj(i -> selectedCard.getCardData().getCardEffect().getFirst().getSource()).toList();
                model.getActionManager().addSourceWithEffect(list, selectedCard);
            }
        }
    }

    private Choice getFactionCardChoice(String path, int index) {
        String cardId = path.substring(0, 3) + (model.getGameData().getFactionCards().get(index) < 10 ? "00" : "0") + model.getGameData().getFactionCards().get(index);
        return new Choice(new Card(String.format("%s/%s", path, cardId), cardId, model), () -> {
            model.getActionManager().drawFactionCard(model.getGameData().getFactionCards().get(index));
            Collections.shuffle(model.getGameData().getFactionCards());
        });
    }

    private Choice getSourceChoice(CardEffects effect) {
        Node node;
        List<Sources> sourcesList;
        if (CardEffects.TWO_SCORE_POINTS.equals(effect)) {
            VBox vBox = new VBox();
            IntStream.range(0, 2).forEach(i -> {
                ImageNode imageNode = new ImageNode(model.getUIConfig().getCardWidth(), model.getUIConfig().getCardHeight() / 2);
                imageNode.setImage("source/score_point");
                vBox.getChildren().add(imageNode.getImageView());
            });
            node = vBox;
            sourcesList = List.of(Sources.SCORE_POINT, Sources.SCORE_POINT);
        } else {
            ImageNode imageNode = new ImageNode(model.getUIConfig().getCardWidth(), model.getUIConfig().getCardHeight());
            imageNode.setImage("source/" + effect.getSource());
            node = imageNode.getImageView();
            sourcesList = selectedCard.getCardData().getCardEffect().stream().map(CardEffects::getSource).toList();
        }
        return new Choice(node, () ->
                model.getActionManager().addSourceWithEffect(sourcesList, selectedCard));
    }

    private record HighlightMessenger(ObservableList<Node> list, ScrollPane scrollPane, double hValue, double vValue,
                                      ProductionBlocks nextProductionBlock, int index, int finish,
                                      Runnable processProduction, long time) {

    }
}
