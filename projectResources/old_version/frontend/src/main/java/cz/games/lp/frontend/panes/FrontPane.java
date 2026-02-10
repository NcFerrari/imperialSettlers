package cz.games.lp.frontend.panes;

import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.layout.Pane;

public class FrontPane extends Pane {

    public FrontPane(CommonModel model) {
        setMouseTransparent(true);
        model.setFrontPane(this);
    }
}
