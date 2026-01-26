package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.FactionService;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.FactionTypes;
import cz.games.lp.gamecore.FactionChooser;
import cz.games.lp.gamecore.GameManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FactionServiceImpl implements FactionService {

    private final FactionChooser factionChooser;
    private final GameManager gameManager;

    public FactionServiceImpl(FactionChooser factionChooser, GameManager gameManager) {
        this.factionChooser = factionChooser;
        this.gameManager = gameManager;
    }

    @Override
    public List<FactionTypes> getRemainingFactions() {
        log.debug("getRemainingFactions");
        return factionChooser.getRemainingFactions();
    }

    private void removeSelectedFaction(FactionTypes faction) {
        log.debug("removeSelectedFaction");
        factionChooser.removeFromChoice(faction);
    }

    @Override
    public void resetFactionSelection() {
        log.debug("resetFactionSelection");
        factionChooser.newGame();
    }

    @Override
    public FactionDTO getFactionFromCurrentPlayer() {
        log.debug("getFactionFromCurrentPlayer");
        return gameManager.getCurrentPlayer().getFaction();
    }

    @Override
    public void selectFactionForCurrentPlayer(FactionTypes faction) {
        log.debug("selectFactionForCurrentPlayer");
        gameManager.getCurrentPlayer().selectFaction(gameManager.getFactionCatalog().factionMap().get(faction));
        removeSelectedFaction(faction);
    }
}
