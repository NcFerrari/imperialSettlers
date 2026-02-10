package cz.games.lp.frontend;

import cz.games.lp.common.enums.Factions;
import cz.games.lp.common.game.GameData;
import cz.games.lp.frontend.api.IManager;
import cz.games.lp.frontend.components.transition_components.FactionToken;
import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.models.CommonModel;
import cz.games.lp.frontend.panes.FrontPane;
import cz.games.lp.frontend.panes.UIPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static IManager manager;
    private static GameData gameData;

    private final CommonModel model = new CommonModel();

    public static void run(IManager manager, GameData gameData) {
        MainApp.manager = manager;
        MainApp.gameData = gameData;
        launch();
    }

    @Override
    public void start(Stage stage) {
        StackPane stackPane = new StackPane(new UIPane(model), new FrontPane(model));
        Scene scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.setWidth(model.getUIConfig().getWidth());
        stage.setHeight(model.getUIConfig().getHeight());
        stage.setTitle(Texts.TITLE.get());
        stage.setResizable(false);
        stage.show();

        initModel(stage);

        newGame();
    }

    private void initModel(Stage stage) {
        model.setManager(manager);
        model.setGameData(gameData);
        model.getChoiceDialog().initOwner(stage);
    }

    private void newGame() {
        clearAll();

        model.getGameData().newGame();
        model.getRoundPhases().reset();
        model.getCommonDeck().createCard(Texts.COMMON.get());

        model.getFactionChoiceDialog().showAndWait();
        initFactionComponents(model.getFactionChoiceDialog().getResult());
        model.getActionManager().prepareFirstFourCards();

        model.getOwnSupplies().forEach((sources, supply) -> supply.setValue(50));
    }

    private void initFactionComponents(Factions factions) {
        model.getGameData().setSelectedFaction(factions);
        model.getRoundPhases().reset();
        model.getFactionBoard().setImage(factions);
        model.getSourcePane().generateNewSources();
        model.getFactionDeck().createCard(factions.getFactionCardPath());
        model.setFactionToken(new FactionToken(model));
    }

    private void clearAll() {
        model.getCardsInHand().clear();
        model.getFactionCards().forEach((cardTypes, cards) -> ((HBox) cards.getContent()).getChildren().clear());
        model.getDeals().clear();
        model.getCommonCards().forEach((cardTypes, cards) -> ((HBox) cards.getContent()).getChildren().clear());
    }
}
