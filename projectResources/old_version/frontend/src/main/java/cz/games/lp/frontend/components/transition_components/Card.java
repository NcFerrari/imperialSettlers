package cz.games.lp.frontend.components.transition_components;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.frontend.components.ImageNode;
import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;

public class Card extends TransitionGroup {

    private final TranslateTransition cardTransition = new TranslateTransition();
    @Getter
    private final Rectangle border;
    @Getter
    private String cardId;
    @Getter
    private boolean samurai;
    private Runnable runnable;
    @Getter
    private CardDTO cardData;
    private final ImageNode samuraiImage;

    public Card(CommonModel model) {
        super(model);

        border = new Rectangle(model.getUIConfig().getCardWidth(), model.getUIConfig().getCardHeight() - model.getUIConfig().getBorderWidth() * 1.5);
        border.setFill(null);
        border.setStrokeWidth(model.getUIConfig().getBorderWidth());
        getChildren().add(border);

        samuraiImage = new ImageNode(model.getUIConfig().getFactionTokenWidth(), model.getUIConfig().getFactionTokenHeight());
        samuraiImage.getImageView().setX(model.getUIConfig().getCardWidth() - model.getUIConfig().getFactionTokenWidth());
        samuraiImage.setImage("source/settler");
    }

    public Card(String path, String cardId, CommonModel model) {
        this(model);
        this.cardId = cardId;
        ImageNode imageNode = new ImageNode(model.getUIConfig().getCardWidth(), model.getUIConfig().getCardHeight());
        imageNode.setImage("cards/" + path);
        getChildren().addFirst(imageNode.getImageView());
        cardTransition.setNode(this);
        loadCardData();
    }

    public void select() {
        border.setStroke(Color.RED);
    }

    public void deselect() {
        border.setStroke(null);
    }

    public void setGoalX(double x) {
        cardTransition.setToX(x);
    }

    public void setGoalY(double y) {
        cardTransition.setToY(y);
    }

    @Override
    protected void playTransition() {
        cardTransition.setDuration(Duration.millis(model.getUIConfig().getCardAnimationSpeed()));
        cardTransition.play();
    }

    @Override
    protected Animation getAnimation() {
        return cardTransition;
    }

    @Override
    protected void additionalFinish() {
        if (runnable != null) {
            runnable.run();
        }
    }

    public void setOnFinishedAdditional(Runnable runnable) {
        this.runnable = runnable;
    }

    private void loadCardData() {
        cardData = model.getManager().getCardData(cardId);
    }

    public void addSamurai() {
        if (!model.getGameData().getSelectedFaction().name().startsWith(Texts.JAPAN.get())) {
            return;
        }
        samurai = true;
        getChildren().add(samuraiImage.getImageView());
    }

    public boolean hasSamurai() {
        return samurai;
    }
}
