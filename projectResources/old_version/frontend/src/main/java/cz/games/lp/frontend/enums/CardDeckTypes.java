package cz.games.lp.frontend.enums;

import cz.games.lp.frontend.models.CommonModel;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public enum CardDeckTypes {

    FACTION(model -> model.getGameData().getFactionCards(), model -> model.getActionManager().drawFactionCard()),
    COMMON(model -> model.getGameData().getCommonCards(), model -> model.getActionManager().drawCommonCard());

    private final Function<CommonModel, List<Integer>> loadCardFunction;
    private final Consumer<CommonModel> drawCardFunction;

    CardDeckTypes(Function<CommonModel, List<Integer>> modelFunction, Consumer<CommonModel> drawCardFunction) {
        this.loadCardFunction = modelFunction;
        this.drawCardFunction = drawCardFunction;
    }

    public List<Integer> getCardList(CommonModel model) {
        return loadCardFunction.apply(model);
    }

    public void drawCard(CommonModel model) {
        if (!getCardList(model).isEmpty()) {
            drawCardFunction.accept(model);
        }
    }
}
