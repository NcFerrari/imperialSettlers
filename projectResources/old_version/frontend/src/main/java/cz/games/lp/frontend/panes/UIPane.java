package cz.games.lp.frontend.panes;

import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.layout.BorderPane;

public class UIPane extends BorderPane {

    public UIPane(CommonModel model) {
        setStyle(model.getUIConfig().getStyle());
        setTop(model.getSourcePane());
        setRight(new CPUPane(model));
        setCenter(new CenterPane(model));
        setBottom(new BottomPane(model));
    }
}
