package cz.games.lp.frontend.components;

import cz.games.lp.common.enums.Sources;
import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import lombok.Getter;

public class Supply extends Group {

    private Label valueLabel;
    @Getter
    private int value = 0;

    public Supply(Sources source, CommonModel model) {
        ImageNode imageNode = new ImageNode(model.getUIConfig().getSupplyWidth(), model.getUIConfig().getSupplyHeight());
        imageNode.setImage("source/" + source.name().toLowerCase());
        getChildren().add(imageNode.getImageView());

        createLabel(imageNode.getImageView().getFitWidth(), model);
    }

    private void createLabel(double imageWidth, CommonModel model) {
        valueLabel = new Label("" + value);
        valueLabel.setFont(new Font("StencilStd", 3 * model.getUIConfig().getHeight() / 49));
        valueLabel.setStyle("-fx-text-fill: BLACK");
        valueLabel.setPrefWidth(model.getUIConfig().getSupplyWidth());
        valueLabel.setPrefHeight(model.getUIConfig().getSupplyHeight());
        valueLabel.setLayoutX(imageWidth);
        getChildren().add(valueLabel);
    }

    public void addOne() {
        setValue(getValue() + 1);
    }

    public void removeOne() {
        setValue(getValue() - 1);
    }

    public void setValue(int value) {
        this.value = value;
        valueLabel.setText("" + value);
    }
}
