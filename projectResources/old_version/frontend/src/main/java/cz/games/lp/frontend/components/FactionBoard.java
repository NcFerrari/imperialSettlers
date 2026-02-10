package cz.games.lp.frontend.components;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.Factions;
import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FactionBoard extends Group {

    private final CommonModel model;
    private final ImageNode imageNode;
    private final Rectangle border;
    private Factions faction;

    public FactionBoard(CommonModel model) {
        this.model = model;
        border = new Rectangle(
                model.getUIConfig().getFactionBoardBorderX(),
                model.getUIConfig().getFactionBoardBorderY(),
                model.getUIConfig().getFactionBoardBorderWidth(),
                model.getUIConfig().getFactionBoardBorderHeight());
        border.setFill(null);
        border.setStrokeWidth(model.getUIConfig().getBorderWidth());
        imageNode = new ImageNode(
                model.getUIConfig().getCardWidth(),
                3 * model.getUIConfig().getCardHeight(),
                5 * model.getUIConfig().getCardWidth() / 3,
                5 * model.getUIConfig().getCardHeight());
        getChildren().addAll(imageNode.getImageView(), border);
    }

    public void setImage(Factions faction) {
        this.faction = faction;
        imageNode.setImage("factions/faction_boards/" + faction.getBoardImage());
    }

    public void select() {
        border.setStroke(Color.RED);
    }

    public void deselect() {
        border.setStroke(null);
    }

    public FactionDTO getFactionData() {
        return model.getManager().getFaction(faction.name());
    }
}
