package cz.games.lp.frontend.models;

import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.common.enums.Sources;
import cz.games.lp.common.game.GameData;
import cz.games.lp.frontend.game_actions.ActionManager;
import cz.games.lp.frontend.api.IManager;
import cz.games.lp.frontend.components.ChoiceDialog;
import cz.games.lp.frontend.components.FactionBoard;
import cz.games.lp.frontend.components.FactionChoiceDialog;
import cz.games.lp.frontend.components.RoundPhases;
import cz.games.lp.frontend.components.ScoreAndRoundBoard;
import cz.games.lp.frontend.components.Supply;
import cz.games.lp.frontend.components.transition_components.FactionToken;
import cz.games.lp.frontend.components.transition_components.RoundPointer;
import cz.games.lp.frontend.enums.CardDeckTypes;
import cz.games.lp.frontend.panes.CardDeckPane;
import cz.games.lp.frontend.panes.CardsInHandPane;
import cz.games.lp.frontend.panes.DealPane;
import cz.games.lp.frontend.panes.SourcePane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class CommonModel {

    private final Map<Sources, Supply> ownSupplies = new LinkedHashMap<>();
    private final Map<CardTypes, ScrollPane> factionCards = new EnumMap<>(CardTypes.class);
    private final Map<CardTypes, ScrollPane> commonCards = new EnumMap<>(CardTypes.class);
    private final UIConfig uIConfig = new UIConfig();
    private final ActionManager actionManager = new ActionManager(this);
    private final ChoiceDialog choiceDialog = new ChoiceDialog(this);
    private final FactionChoiceDialog factionChoiceDialog = new FactionChoiceDialog(this);
    private final SourcePane sourcePane = new SourcePane(this);
    private final CardDeckPane factionDeck = new CardDeckPane(this, CardDeckTypes.FACTION);
    private final CardDeckPane commonDeck = new CardDeckPane(this, CardDeckTypes.COMMON);
    private final ScoreAndRoundBoard scoreBoard = new ScoreAndRoundBoard(this);
    private final RoundPhases roundPhases = new RoundPhases(this);
    private final DealPane deals = new DealPane(this);
    private final FactionBoard factionBoard = new FactionBoard(this);
    private final CardsInHandPane cardsInHand = new CardsInHandPane(this);
    private IManager manager;
    private RoundPointer roundPointer;
    private FactionToken factionToken;
    private Pane frontPane;
    private GameData gameData;
    private boolean transitionRunning;
    private boolean sequentialRunning;
}
