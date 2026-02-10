package cz.games.lp.frontend.components.transition_components;

import cz.games.lp.frontend.components.ImageNode;
import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;

public class FactionToken extends TransitionGroup {

    private final TranslateTransition scoreTransition = new TranslateTransition();
    private ImageNode token;
    private int score;
    private double scoreXPosition;
    private double scoreYPosition;
    private int xMove;

    public FactionToken(CommonModel model) {
        super(model);
        createFactionToken(false);
    }

    private void createFactionToken(boolean fiftyPlus) {
        if (token != null) {
            model.getScoreBoard().getChildren().remove(token.getImageView());
        }
        score = 0;
        scoreXPosition = 0;
        scoreYPosition = 0;
        xMove = 1;
        token = new ImageNode(model.getUIConfig().getFactionTokenWidth(), model.getUIConfig().getFactionTokenHeight());
        token.getImageView().setX(model.getUIConfig().getFactionTokenXMove());
        token.getImageView().setY(model.getUIConfig().getFactionTokenYMove());
        String fifty = fiftyPlus ? "50" : "";
        token.setImage("factions/faction_tokens/" + model.getGameData().getSelectedFaction().getTokenImage() + fifty);
        scoreTransition.setNode(token.getImageView());
        model.getScoreBoard().getChildren().add(token.getImageView());
    }

    @Override
    protected void additionalFinish() {
        if (score == 50) {
            createFactionToken(true);
        }
    }

    @Override
    protected void playTransition() {
        score++;
        if (score % 10 == 0) {
            scoreYPosition++;
            xMove = -xMove;
        } else {
            scoreXPosition += xMove;
            scoreTransition.setToX(model.getUIConfig().getScoreXMove() * scoreXPosition);
        }
        scoreTransition.setFromX(token.getImageView().getTranslateX());
        scoreTransition.setFromY(token.getImageView().getTranslateY());
        scoreTransition.setToY(scoreYPosition * model.getUIConfig().getScoreYMove());
        scoreTransition.play();
    }

    @Override
    protected Animation getAnimation() {
        return scoreTransition;
    }
}
