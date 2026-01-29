package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.GameService;
import cz.games.lp.backend.service.ProductionService;
import cz.games.lp.gamecore.actions.CardActions;
import cz.games.lp.gamecore.GameManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final GameManager gameManager;
    private final CardActions cardActions;
    private final ProductionService productionService;

    public GameServiceImpl(GameManager gameManager, CardActions cardActions, ProductionService productionService) {
        this.gameManager = gameManager;
        this.cardActions = cardActions;
        this.productionService = productionService;
    }

    @Override
    public void newGame() {
        log.debug("newGame");
        gameManager.newGame();
        cardActions.createNewCardDeck();
    }

    @Override
    public void performLookoutPhase() {
        log.debug("performLookoutPhase");
        cardActions.performLookoutPhase();
    }

    @Override
    public void performProductionPhase() {
        log.debug("performProductionPhase");
        productionService.performProductionPhase();
    }
}
