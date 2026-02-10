package cz.games.lp.frontend.components;

import cz.games.lp.frontend.effects.SelectEffect;
import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class ChoiceDialog extends Dialog<Void> {

    private final HBox contentPane;
    private final CommonModel model;

    public ChoiceDialog(CommonModel model) {
        this.model = model;
        setTitle(Texts.CHOICE.get());
        setResizable(false);
        setWidth(model.getUIConfig().getCardWidth() * 2 + model.getUIConfig().getDialogAdditionalWidth());
        setHeight(model.getUIConfig().getCardHeight() + model.getUIConfig().getDialogAdditionalHeight());
        contentPane = new HBox();
        ScrollPane scrollPane = new ScrollPane(contentPane);
        getDialogPane().setContent(scrollPane);
    }

    public void showDialog(List<Choice> choices) {
        int size = Math.min(choices.size(), 7);
        setWidth(model.getUIConfig().getCardWidth() * size + model.getUIConfig().getDialogAdditionalWidth());
        contentPane.getChildren().clear();
        choices.forEach(choice -> {
            SelectEffect.setSelectEffect(choice.node());
            choice.node().setOnMousePressed(e -> {
                getDialogPane().getScene().getWindow().hide();
                choice.runnable().run();
            });
            contentPane.getChildren().add(choice.node());
        });
        show();
    }
}
