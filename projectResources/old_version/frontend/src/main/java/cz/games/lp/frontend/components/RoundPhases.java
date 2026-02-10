package cz.games.lp.frontend.components;

import cz.games.lp.common.enums.Phases;
import cz.games.lp.frontend.models.CommonModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.LinkedHashMap;
import java.util.Map;

public class RoundPhases extends VBox {

    private static final Phases[] ORDERED_PHASES = new Phases[]{Phases.LOOKOUT, Phases.PRODUCTION, Phases.ACTION, Phases.PASS_ACTION, Phases.CLEANUP};
    private final Map<Phases, Button> buttons = new LinkedHashMap<>();
    private final CommonModel model;

    public RoundPhases(CommonModel model) {
        this.model = model;
        setPrefWidth(model.getUIConfig().getRoundPhasesButtonsWidth());
        setAlignment(Pos.CENTER);
        for (Phases phase : ORDERED_PHASES) {
            ImageNode imageNode = new ImageNode(model.getUIConfig().getPhaseButtonWidth(), model.getUIConfig().getPhaseButtonHeight());
            imageNode.setImage("phase_buttons/" + phase.name());
            Button button = new Button();
            button.setGraphic(imageNode.getImageView());
            button.setOnAction(evt -> {
                button.setDisable(true);
                addButtonListener(phase);
            });
            buttons.put(phase, button);
            getChildren().add(button);
        }
    }

    public void reset() {
        enabledOneButtonOnly(null);
        model.getRoundPointer().execute();
    }

    public void enableButtonForCurrentPhase() {
        enabledOneButtonOnly(model.getGameData().getCurrentPhase());
    }

    public void enableButtonForFollowingPhase() {
        enabledOneButtonOnly(model.getGameData().getCurrentPhase().getFollowingPhase());
    }

    private void enabledOneButtonOnly(Phases enabledPhase) {
        buttons.forEach((phase, button) -> button.setDisable(true));
        if (enabledPhase == null) {
            return;
        }
        buttons.get(enabledPhase).setDisable(false);
        model.getGameData().setCurrentPhase(enabledPhase);
    }

    private void addButtonListener(Phases phase) {
        switch (phase) {
            case LOOKOUT -> model.getActionManager().lookoutPhase();
            case PRODUCTION -> model.getActionManager().productionPhase();
            case ACTION -> model.getCardsInHand().enableSelection();
            case PASS_ACTION -> model.getCardsInHand().disabledSelection();
            case CLEANUP -> {
                model.getGameData().nextRound();
                if (model.getGameData().getRound() < 6) {
                    model.getRoundPointer().execute();
                    enableButtonForFollowingPhase();
                } else {
                    enabledOneButtonOnly(null);
                }
            }
        }
    }
}
