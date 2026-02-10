package cz.games.lp.frontend.components;

import cz.games.lp.common.enums.Factions;
import cz.games.lp.frontend.effects.SelectEffect;
import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;

public class FactionChoiceDialog extends Dialog<Factions> {

    private final Factions[] allFactions = new Factions[]{Factions.BARBARIAN_M, Factions.BARBARIAN_F, Factions.JAPAN_M, Factions.JAPAN_F, Factions.ROMAN_M, Factions.ROMAN_F, Factions.EGYPT_M, Factions.EGYPT_F};
    private final CommonModel model;

    public FactionChoiceDialog(CommonModel model) {
        this.model = model;
        setTitle(Texts.FACTION_CHOICE_TITLE.get());
        getDialogPane().setContent(createDialogPane());
    }

    private HBox createDialogPane() {
        HBox hBox = new HBox();
        for (Factions faction : allFactions) {
            FactionBoard factionBoard = new FactionBoard(model);
            factionBoard.setImage(faction);
            hBox.getChildren().add(factionBoard);
            SelectEffect.setSelectEffect(factionBoard);
            factionBoard.setOnMouseReleased(evt -> {
                factionBoard.setEffect(null);
                setResult(faction);
                close();
            });
        }
        return hBox;
    }
}
