package cz.games.lp.frontend.components.transition_components;

import cz.games.lp.frontend.components.ImageNode;
import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import lombok.Getter;

public class RoundPointer extends TransitionGroup {

    private final TranslateTransition roundTransition = new TranslateTransition();
    @Getter
    private final ImageNode pointer;

    public RoundPointer(double x, double y, CommonModel model) {
        super(model);
        pointer = new ImageNode(model.getUIConfig().getScoreBoardPointerWidth(), model.getUIConfig().getScoreBoardPointerHeight());
        pointer.setImage("round_pointer");
        pointer.getImageView().setX(x);
        pointer.getImageView().setY(y);
        roundTransition.setNode(pointer.getImageView());
    }

    @Override
    protected void playTransition() {
        if (model.getGameData().getRound() < 1 || model.getGameData().getRound() > 5) {
            model.setTransitionRunning(false);
            return;
        }
        double newPosition = model.getUIConfig().getScoreYMove() * (model.getGameData().getRound() - 1);
        roundTransition.setFromY(pointer.getImageView().getTranslateY());
        roundTransition.setToY(newPosition);
        roundTransition.setDuration(Duration.millis(model.getUIConfig().getCardAnimationSpeed()));
        roundTransition.play();
    }

    @Override
    public Animation getAnimation() {
        return roundTransition;
    }
}
