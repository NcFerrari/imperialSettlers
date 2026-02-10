package cz.games.lp.frontend.panes;

import cz.games.lp.common.enums.Phases;
import cz.games.lp.common.enums.Sources;
import cz.games.lp.frontend.components.ImageNode;
import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.effects.SelectEffect;
import cz.games.lp.frontend.enums.Actions;
import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CardsInHandPane extends ScrollPane {

    private static final Actions[] actions = {Actions.BUILD_LOCATION, Actions.MAKE_A_DEAL, Actions.RAZE};
    private final HBox cards = new HBox();
    private final Map<Actions, MenuItem> menuItemMap = new EnumMap<>(Actions.class);
    private final Map<Sources, Integer> neededSources = new EnumMap<>(Sources.class);
    private final ContextMenu contextMenu = new ContextMenu();
    private final AtomicInteger missingSourcesCount = new AtomicInteger();
    private final AtomicBoolean needLocationForBuild = new AtomicBoolean();
    private final CommonModel model;

    public CardsInHandPane(CommonModel model) {
        this.model = model;
        setPrefWidth(model.getUIConfig().getCardInHandsWidth());
        setStyle(model.getUIConfig().getStyle());
        setContent(cards);
        IntStream.range(0, 3).forEach(i -> {
            ImageNode imageNode = new ImageNode(model.getUIConfig().getPhaseButtonWidth(), model.getUIConfig().getPhaseButtonHeight());
            imageNode.setImage("action_buttons/" + actions[i].getImagePath());
            MenuItem menuItem = new MenuItem();
            menuItem.setGraphic(imageNode.getImageView());
            menuItemMap.put(actions[i], menuItem);
        });
    }

    public void addCard(Card card) {
        cards.getChildren().add(card);
        addListeners(card);
    }

    private void addListeners(Card card) {
        card.setOnMousePressed(evt -> proceedContextMenu(card, evt.getScreenX(), evt.getScreenY()));
    }

    private void proceedContextMenu(Card card, double x, double y) {
        if (!Phases.PASS_ACTION.equals(model.getGameData().getCurrentPhase())) {
            return;
        }
        setContextForCard(card);
        checkPossibleButtonsForContextMenu(card);
        addActionsForContexts(card);
        contextMenu.show(card, x, y);
    }

    private void addActionsForContexts(Card card) {
        menuItemMap.get(Actions.BUILD_LOCATION).setOnAction(evt -> model.getActionManager().buildLocation(card));
        menuItemMap.get(Actions.MAKE_A_DEAL).setOnAction(evt -> model.getActionManager().makeADeal(card));
        menuItemMap.get(Actions.RAZE).setOnAction(evt -> model.getActionManager().raze(card));
    }

    private void checkPossibleButtonsForContextMenu(Card card) {
        resetAndFillLocalVariables(card);
        checkPossibilityToBuilding();
        checkPossibilityToMakeADeal();
        checkPossibilityToRaze();
    }

    private void checkPossibilityToBuilding() {
        boolean hasLocations = Stream.concat(model.getFactionCards().values().stream(), model.getCommonCards().values().stream()).anyMatch(scrollPane -> !((HBox) scrollPane.getContent()).getChildren().isEmpty());
        neededSources.forEach((source, value) -> {
            if ((source == Sources.LOCATION)) {
                needLocationForBuild.set(true);
            }
            if (source != Sources.LOCATION && model.getOwnSupplies().get(source).getValue() < value) {
                missingSourcesCount.set(missingSourcesCount.get() + value - model.getOwnSupplies().get(source).getValue());
            }
        });
        if (missingSourcesCount.get() > model.getOwnSupplies().get(Sources.GOLD).getValue() || (needLocationForBuild.get() && !hasLocations)) {
            menuItemMap.get(Actions.BUILD_LOCATION).setDisable(true);
        }
    }

    private void checkPossibilityToMakeADeal() {
        if (menuItemMap.containsKey(Actions.MAKE_A_DEAL) && model.getOwnSupplies().get(Sources.FOOD).getValue() == 0 && model.getOwnSupplies().get(Sources.GOLD).getValue() == 0) {
            menuItemMap.get(Actions.MAKE_A_DEAL).setDisable(true);
        }
    }

    private void checkPossibilityToRaze() {
        if (model.getOwnSupplies().get(Sources.SWORD).getValue() == 0) {
            menuItemMap.get(Actions.RAZE).setDisable(true);
        }
    }

    private void resetAndFillLocalVariables(Card card) {
        neededSources.clear();
        menuItemMap.values().forEach(menuItem -> menuItem.setDisable(false));
        card.getCardData().getSourcesForBuild().forEach(source -> neededSources.merge(source, 1, Integer::sum));
        missingSourcesCount.set(0);
        needLocationForBuild.set(false);
    }

    private void setContextForCard(Card card) {
        contextMenu.getItems().clear();
        contextMenu.getItems().add(menuItemMap.get(Actions.BUILD_LOCATION));
        if (card.getCardData().getDealSource() != null) {
            contextMenu.getItems().add(menuItemMap.get(Actions.MAKE_A_DEAL));
        }
        if (!card.getCardData().getSourcesFromDestroy().isEmpty()) {
            contextMenu.getItems().add(menuItemMap.get(Actions.RAZE));
        }
    }

    public void clear() {
        cards.getChildren().clear();
    }

    public void enableSelection() {
        cards.getChildren().forEach(SelectEffect::setSelectEffect);
        model.getRoundPhases().enableButtonForFollowingPhase();
    }

    public void disabledSelection() {
        cards.getChildren().forEach(SelectEffect::disableSelectionEffect);
        model.getRoundPhases().enableButtonForFollowingPhase();
    }

    public void razeCard(Card card) {
        cards.getChildren().remove(card);
    }
}
