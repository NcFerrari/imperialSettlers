package cz.games.lp.frontend.panes;

import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.control.ScrollPane;

public class CPUPane extends ScrollPane {

    public CPUPane(CommonModel model) {
        setPrefWidth(model.getUIConfig().getCardWidth());
        setStyle(model.getUIConfig().getStyle());
    }
}
