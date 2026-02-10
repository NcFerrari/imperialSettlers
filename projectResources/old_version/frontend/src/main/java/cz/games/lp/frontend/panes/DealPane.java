package cz.games.lp.frontend.panes;

import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.models.CommonModel;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class DealPane extends Group {

    private final VBox cardPane;
    private final ScrollPane basicScrollPane;

    public DealPane(CommonModel model) {
        basicScrollPane = new ScrollPane();
        basicScrollPane.setPrefWidth(model.getUIConfig().getCardWidth());
        basicScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        basicScrollPane.setMaxHeight(model.getUIConfig().getScoreBoardHeight());
        basicScrollPane.setStyle(model.getUIConfig().getStyle());
        cardPane = new VBox();
        cardPane.setPadding(new Insets(0, 0, model.getUIConfig().getDealSpacing(), 0));
        cardPane.setAlignment(Pos.BOTTOM_CENTER);
        cardPane.setPrefHeight(model.getUIConfig().getScoreBoardHeight() - 10);
        cardPane.setSpacing(model.getUIConfig().getDealSpacing());
        basicScrollPane.setContent(cardPane);
        getChildren().add(basicScrollPane);
    }

    public void makeADeal(Card card) {
        card.setRotate(180);
        cardPane.getChildren().add(card);
    }

    public ObservableList<Node> getCards() {
        return cardPane.getChildren();
    }

    public void clear() {
        cardPane.getChildren().clear();
    }

    public ScrollPane getScrollPane() {
        return basicScrollPane;
    }
}
