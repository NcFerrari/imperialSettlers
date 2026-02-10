package cz.games.lp.frontend.panes;

import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class CenterPane extends VBox {

    public CenterPane(CommonModel model) {
        HBox incomingPane = new HBox();
        VBox.setVgrow(incomingPane, Priority.ALWAYS);
        Region space = new Region();
        space.setPrefWidth(model.getUIConfig().getSpace());
        incomingPane.getChildren().addAll(
                model.getScoreBoard(),
                model.getRoundPhases(),
                model.getFactionDeck(),
                model.getDeals(),
                model.getCommonDeck(),
                space,
                model.getCardsInHand()
        );
        getChildren().add(incomingPane);
    }
}
