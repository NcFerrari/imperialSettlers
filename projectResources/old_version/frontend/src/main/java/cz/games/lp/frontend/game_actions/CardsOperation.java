package cz.games.lp.frontend.game_actions;

import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CardsOperation {

    public static int getCardsCountWithCondition(CommonModel model, Predicate<Card> condition, int limit) {
        int sum = 0;
        sum += (int) Stream.of(
                        model.getFactionCards().values(),
                        model.getCommonCards().values()
                )
                .flatMap(Collection::stream)
                .map(ScrollPane::getContent)
                .filter(HBox.class::isInstance)
                .map(HBox.class::cast)
                .flatMap(hbox -> hbox.getChildren().stream())
                .filter(Card.class::isInstance)
                .map(Card.class::cast)
                .filter(condition)
                .limit(limit)
                .count();
        return sum;
    }

    private CardsOperation() {

    }

}
