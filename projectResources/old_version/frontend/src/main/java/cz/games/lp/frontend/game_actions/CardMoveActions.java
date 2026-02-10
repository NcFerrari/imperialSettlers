package cz.games.lp.frontend.game_actions;

import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.models.CommonModel;
import cz.games.lp.frontend.panes.CardDeckPane;
import javafx.animation.ScaleTransition;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.List;

public record CardMoveActions(CommonModel model) {

    public void drawCard(CardDeckPane cardDeck, String cardPath, List<Integer> cardNumbers, int cardNumber) {
        if (model.isTransitionRunning() || cardNumbers.isEmpty()) {
            return;
        }
        Card card = cardDeck.getMovingCard();
        model.getFrontPane().getChildren().add(card);
        Bounds boundsFrom = cardDeck.getChildren().getFirst().localToScene(cardDeck.getChildren().getFirst().getBoundsInLocal());
        card.setLayoutX(boundsFrom.getMinX());
        card.setLayoutY(boundsFrom.getMinY());
        card.setTranslateX(0);
        card.setTranslateY(0);
        card.setGoalX(model.getCardsInHand().localToScene(model.getCardsInHand().getBoundsInLocal()).getMinX() - boundsFrom.getMinX());
        card.setGoalY(model.getCardsInHand().localToScene(model.getCardsInHand().getBoundsInLocal()).getMinY() - boundsFrom.getMinY());
        card.setOnFinishedAdditional(() -> {
            model.getFrontPane().getChildren().remove(card);
            String cardId = String.format("%s0%s%d", cardPath.substring(0, 3), (cardNumber < 10 ? "0" : ""), cardNumber);
            model.getCardsInHand().addCard(new Card(cardPath + "/" + cardId, cardId, model));
            cardDeck.removeCard(cardNumber);
        });
        if (cardNumbers.size() == 1) {
            cardDeck.removeDeckCard();
        }
        card.execute();
    }

    public void moveCard(Card card, Node goalNode) {
        Bounds boundsFrom = card.localToScene(card.getBoundsInLocal());
        model.getFrontPane().getChildren().add(card);
        card.setLayoutX(boundsFrom.getMinX());
        card.setLayoutY(boundsFrom.getMinY());
        card.setTranslateX(0);
        card.setTranslateY(0);
        card.setGoalX(goalNode.localToScene(goalNode.getBoundsInLocal()).getMinX() - boundsFrom.getMinX());
        card.setGoalY(goalNode.localToScene(goalNode.getBoundsInLocal()).getMinY() - boundsFrom.getMinY());
        card.execute();
    }

    public void reduceCard(Card card, Runnable runnable) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(model.getUIConfig().getAnimationSpeed()), card);
        scaleTransition.setToX(0);
        scaleTransition.setToY(0);
        scaleTransition.setOnFinished(e -> runnable.run());
        scaleTransition.play();
    }
}
