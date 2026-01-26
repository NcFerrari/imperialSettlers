package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.FactionService;
import cz.games.lp.common.enums.FactionTypes;
import cz.games.lp.gamecore.FactionChooser;
import cz.games.lp.gamecore.GameManager;
import cz.games.lp.gamecore.components.Player;
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

    @Override
    public void removeSelectedFaction(FactionTypes faction) {
        log.debug("removeSelectedFaction");
        factionChooser.removeFromChoice(faction);
    }

    @Override
    public void selectFaction(Player player, FactionTypes faction) {
        log.debug("selectFaction");
        player.selectFaction(gameManager.getFactionCatalog().factionMap().get(faction));
        removeSelectedFaction(faction);
    }

    @Override
    public void resetFactionSelection() {
        log.debug("resetFactionSelection");
        factionChooser.newGame();
    }
}
