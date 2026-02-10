package cz.games.lp.frontend.effects;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.Lighting;

public class SelectEffect {

    public static void setSelectEffect(Node node) {
        node.setOnMouseEntered(e -> {
            node.setEffect(new Lighting());
            node.setCursor(Cursor.HAND);
        });
        node.setOnMouseExited(e -> {
            node.setEffect(null);
            node.setCursor(Cursor.DEFAULT);
        });
    }

    public static void disableSelectionEffect(Node node) {
        node.setOnMouseEntered(null);
        node.setOnMouseExited(null);
    }

    private SelectEffect() {
    }
}
