package cz.games.lp.frontend.panes;

import cz.games.lp.common.enums.Sources;
import cz.games.lp.frontend.components.Supply;
import cz.games.lp.frontend.models.CommonModel;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;

import java.util.List;

public class SourcePane extends FlowPane {

    private final CommonModel model;

    public SourcePane(CommonModel model) {
        this.model = model;
        setAlignment(Pos.CENTER);
        setStyle(model.getUIConfig().getHeaderStyle());
    }

    public void generateNewSources() {
        if (model.getGameData().getSelectedFaction() == null) {
            return;
        }
        model.getOwnSupplies().clear();
        for (Sources source : model.getGameData().getSelectedFaction().getSourcesInOwnSupply()) {
            model.getOwnSupplies().put(source, new Supply(source, model));
        }
        getChildren().clear();
        addScrollBar();
        addSourcesIntoPane();
    }

    private void addScrollBar() {
        Slider slider = new Slider();
        slider.setMax(model.getUIConfig().getMaxAnimationSpeed());
        slider.setMin(model.getUIConfig().getMinAnimationSpeed());
        slider.setMajorTickUnit(model.getUIConfig().getTickAnimationSpeed());
        slider.setValue(model.getUIConfig().getAnimationSpeed());
        slider.valueProperty().addListener((observable, oldValue, newValue) -> model.getUIConfig().setAnimationSpeed(newValue.intValue()));
        getChildren().add(slider);
    }

    private void addSourcesIntoPane() {
        List<Supply> supplies = model.getOwnSupplies()
                .values()
                .stream()
                .toList();
        getChildren().addAll(supplies);
    }
}
