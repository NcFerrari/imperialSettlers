package cz.games.lp.frontend.components;

import cz.games.lp.frontend.components.transition_components.RoundPointer;
import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.Group;
import lombok.Getter;

public class ScoreAndRoundBoard extends Group {

    private final CommonModel model;
    @Getter
    private ImageNode board;

    public ScoreAndRoundBoard(CommonModel model) {
        this.model = model;
        initBoard();
        model.setRoundPointer(createRoundPointer());
    }

    private void initBoard() {
        board = new ImageNode(
                model.getUIConfig().getScoreBoardWidth(),
                model.getUIConfig().getScoreBoardHeight(),
                30 * model.getUIConfig().getScoreBoardWidth() / 13,
                30 * model.getUIConfig().getScoreBoardHeight() / 13);
        board.setImage("score_board");
        getChildren().add(board.getImageView());
    }

    public RoundPointer createRoundPointer() {
        RoundPointer roundPointer = new RoundPointer(
                getBoard().getImageView().getFitWidth() - model.getUIConfig().getScoreBoardPointerWidthSpace(),
                model.getUIConfig().getScoreBoardPointerHeightSpace(),
                model);
        getChildren().add(roundPointer.getPointer().getImageView());
        return roundPointer;
    }
}
