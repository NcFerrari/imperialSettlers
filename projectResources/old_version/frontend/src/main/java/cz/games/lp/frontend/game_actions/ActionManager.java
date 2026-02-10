package cz.games.lp.frontend.game_actions;

import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.common.enums.Sources;
import cz.games.lp.frontend.components.Choice;
import cz.games.lp.frontend.components.ImageNode;
import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.enums.CardDeckTypes;
import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ActionManager extends AnimationTimer {

    private final CardMoveActions cardMoveActions;
    private final ProductionActions productionActions;
    private final CommonModel model;
    private final AtomicInteger counter = new AtomicInteger();
    private final AnimationTimer pointAnimation;
    @Getter
    private boolean pointAnimationIsRunning;
    @Setter
    private int scorePointToAdd;
    @Setter
    @Getter
    private boolean animationRunning;
    private Consumer<Long> consumerMethod;

    public ActionManager(CommonModel model) {
        this.model = model;
        cardMoveActions = new CardMoveActions(model);
        productionActions = new ProductionActions(model);
        pointAnimation = new AnimationTimer() {
            @Override
            public void handle(long time) {
                if (scorePointToAdd == 0) {
                    pointAnimationIsRunning = false;
                    stop();
                    return;
                }
                if (!model.isTransitionRunning()) {
                    model.getFactionToken().execute();
                    scorePointToAdd--;
                }
            }
        };
    }

    public void drawFactionCard(int cardNumber) {
        cardMoveActions.drawCard(model.getFactionDeck(), model.getGameData().getSelectedFaction().getFactionCardPath(), model.getGameData().getFactionCards(), cardNumber);
    }

    public void drawFactionCard() {
        drawFactionCard(model.getGameData().getFactionCards().getFirst());
    }

    public void drawCommonCard() {
        cardMoveActions.drawCard(model.getCommonDeck(), Texts.COMMON.get(), model.getGameData().getCommonCards(), model.getGameData().getCommonCards().getFirst());
    }

    public void prepareFirstFourCards() {
        dealCards(4, () -> model.getRoundPhases().enableButtonForCurrentPhase());
    }

    public void lookoutPhase() {
        dealCards(3, () -> model.getRoundPhases().enableButtonForFollowingPhase());
    }

    private void dealCards(int numberOfCards, Runnable runnable) {
        counter.set(numberOfCards);
        consumerMethod = time -> {
            switch (counter.getAndDecrement()) {
                case 3, 4 -> CardDeckTypes.FACTION.drawCard(model);
                case 1, 2 -> CardDeckTypes.COMMON.drawCard(model);
                default -> {
                    stop();
                    runnable.run();
                }
            }
        };
        start();
    }

    @Override
    public void handle(long time) {
        if (model.isTransitionRunning()) {
            return;
        }
        consumerMethod.accept(time);
    }

    public void productionPhase() {
        if (animationRunning) {
            return;
        }
        setAnimationRunning(true);
        consumerMethod = productionActions.proceedProduction();
        start();
    }

    public void addSourceWithEffect(List<Sources> sources, Group parentForEffect) {
        SequentialTransition sequentialTransition = new SequentialTransition();
        double delay = model.getUIConfig().getAnimationSpeed();
        sources.forEach(source -> {
            ImageNode imageNode = new ImageNode(model.getUIConfig().getFactionTokenWidth(), model.getUIConfig().getFactionTokenHeight());
            imageNode.setImage("source/" + source.name().toLowerCase());
            imageNode.getImageView().setX(model.getUIConfig().getCardWidth() / 2 - imageNode.getImageView().getFitWidth() / 2);
            imageNode.getImageView().setY(3 * model.getUIConfig().getCardHeight() / 4 - imageNode.getImageView().getFitHeight() / 2);
            imageNode.getImageView().setVisible(false);
            parentForEffect.getChildren().add(imageNode.getImageView());

            Transition transition = new Transition() {
                @Override
                protected void interpolate(double v) {
                    imageNode.getImageView().setVisible(true);
                    switch (source) {
                        case SCORE_POINT -> {
                            model.getActionManager().setScorePointToAdd(1);
                            pointAnimationIsRunning = true;
                            pointAnimation.start();
                        }
                        case COMMON_CARD -> model.getActionManager().drawCommonCard();
                        case CARD -> {
                            boolean hasCommonCards = !model.getGameData().getCommonCards().isEmpty();
                            boolean hasFactionCards = !model.getGameData().getFactionCards().isEmpty();
                            if (hasCommonCards && hasFactionCards) {
                                Choice factionCard = new Choice(new Card(model.getGameData().getSelectedFaction().getFactionCardPath(), null, model), () -> model.getActionManager().drawFactionCard());
                                Choice commonCard = new Choice(new Card(Texts.COMMON.get(), null, model), () -> model.getActionManager().drawCommonCard());
                                model.getChoiceDialog().showDialog(List.of(factionCard, commonCard));
                            } else if (hasCommonCards) {
                                drawCommonCard();
                            } else if (hasFactionCards) {
                                drawFactionCard();
                            }
                        }
                        default -> model.getOwnSupplies().get(source).addOne();
                    }
                }
            };

            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(delay / sources.size()), imageNode.getImageView());
            translateTransition.setToY(-2 * model.getUIConfig().getFactionTokenHeight());

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(delay / sources.size()), imageNode.getImageView());
            fadeTransition.setToValue(0);

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(delay / sources.size()), imageNode.getImageView());
            scaleTransition.setToX(2);
            scaleTransition.setToY(2);

            ParallelTransition parallelTransition = new ParallelTransition();
            parallelTransition.getChildren().addAll(transition, translateTransition, fadeTransition, scaleTransition);
            parallelTransition.setOnFinished(e -> parentForEffect.getChildren().remove(imageNode.getImageView()));

            sequentialTransition.getChildren().add(parallelTransition);
        });
        sequentialTransition.setOnFinished(actionEvent -> model.setSequentialRunning(false));
        model.setSequentialRunning(true);
        sequentialTransition.play();
    }

    public void buildLocation(Card card) {
        card.getCardData().getSourcesForBuild().forEach(this::payForCard);
        HBox hBox;
        if (card.getCardData().getDealSource() == null) {
            hBox = (HBox) model.getCommonCards().get(card.getCardData().getCardType()).getContent();
        } else {
            hBox = (HBox) model.getFactionCards().get(card.getCardData().getCardType()).getContent();
        }
        card.setOnFinishedAdditional(() -> {
            card.setTranslateX(0);
            card.setTranslateY(0);
            hBox.getChildren().add(card);
            card.setOnMousePressed(null);

            if (CardTypes.PRODUCTION.equals(card.getCardData().getCardType())) {
                productionActions.processCard(card);
            }

            if (card.getCardData().getCardEffectForPosition() != null) {
                addSourceWithEffect(List.of(card.getCardData().getCardEffectForPosition().getSource()), card);
            }
        });
        cardMoveActions.moveCard(card, hBox);
    }

    private void payForCard(Sources source) {
        if (Sources.LOCATION.equals(source)) {
        } else {
            model.getOwnSupplies().get(source).removeOne();
        }
    }

    public void makeADeal(Card card) {
        payForCard(Sources.FOOD);
        card.setOnFinishedAdditional(() -> {
            card.setTranslateX(0);
            card.setTranslateY(0);
            model.getDeals().makeADeal(card);
            card.setOnMousePressed(null);
        });
        cardMoveActions.moveCard(card, model.getDeals());
    }

    public void raze(Card card) {
        payForCard(Sources.SWORD);
        card.setOnMousePressed(null);
        counter.set(1);
        if (Sources.CARD.equals(card.getCardData().getSourcesFromDestroy().getFirst())) {
            counter.set(2);
        }
        consumerMethod = time -> {
            if (model.getChoiceDialog().isShowing()) {
                return;
            }
            if (counter.get() == 0) {
                stop();
                cardMoveActions.reduceCard(card, () -> model.getCardsInHand().razeCard(card));
                return;
            }
            counter.getAndDecrement();
            addSourceWithEffect(card.getCardData().getSourcesFromDestroy(), card);
        };
        start();
    }
}