package cz.games.lp.frontend.panes;

import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.frontend.models.CommonModel;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class BottomPane extends HBox {

    private final CardTypes[] cardTypesForImperialSides = {CardTypes.PRODUCTION, CardTypes.PROPERTIES, CardTypes.ACTION};
    private final CommonModel model;

    public BottomPane(CommonModel model) {
        this.model = model;
        setPrefHeight(model.getUIConfig().getBottomPaneHeight());

        VBox factionSide = setSides(model.getFactionCards(), NodeOrientation.RIGHT_TO_LEFT);
        VBox commonSide = setSides(model.getCommonCards(), NodeOrientation.LEFT_TO_RIGHT);

        getChildren().addAll(factionSide, model.getFactionBoard(), commonSide);
    }

    private VBox setSides(Map<CardTypes, ScrollPane> cardPanes, NodeOrientation orientation) {
        VBox sideVBox = new VBox();
        sideVBox.setPrefWidth(model.getUIConfig().getWidthForImperialCardStack());

        for (CardTypes cardType : cardTypesForImperialSides) {
            HBox boxForCards = new HBox();
            boxForCards.setNodeOrientation(orientation);
            boxForCards.setPrefHeight(model.getUIConfig().getCardHeight());

            ScrollPane scrollPane = new ScrollPane(boxForCards);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setStyle(model.getUIConfig().getStyle());
            sideVBox.getChildren().add(scrollPane);
            cardPanes.put(cardType, scrollPane);
        }
        return sideVBox;
    }
}
