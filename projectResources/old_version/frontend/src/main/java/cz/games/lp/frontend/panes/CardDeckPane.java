package cz.games.lp.frontend.panes;

import cz.games.lp.common.enums.Phases;
import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.enums.CardDeckTypes;
import cz.games.lp.frontend.models.CommonModel;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class CardDeckPane extends VBox {

    private final CardDeckTypes cardType;
    private final CommonModel model;
    private Card card;
    @Getter
    private Card movingCard;

    public CardDeckPane(CommonModel model, CardDeckTypes cardType) {
        this.model = model;
        this.cardType = cardType;
        setAlignment(Pos.CENTER);
        setPrefWidth(model.getUIConfig().getCardWidth());
    }

    public void drawCard() {
        if (!Phases.PASS_ACTION.equals(model.getGameData().getCurrentPhase())) {
            return;
        }
        cardType.drawCard(model);
        if (cardType.getCardList(model).size() == 1) {
            card.setVisible(false);
            setOnMouseClicked(null);
        }
    }

    public void createCard(String cardPath) {
        getChildren().clear();
        card = new Card(cardPath, null, model);
        movingCard = new Card(cardPath, null, model);
        setOnMouseClicked(evt -> drawCard());
        getChildren().add(card);
    }

    public void removeCard(Integer cardNumber) {
        cardType.getCardList(model).remove(cardNumber);
    }

    public void removeDeckCard() {
        getChildren().clear();
    }
}
