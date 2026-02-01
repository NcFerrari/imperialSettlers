package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.FactionService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.gamecore.components.enums.FactionTypes;
import cz.games.lp.gamecore.actions.FactionActions;
import cz.games.lp.gamecore.GameRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FactionServiceImpl implements FactionService {

    private final FactionActions factionActions;
    private final PlayerService playerService;

    public FactionServiceImpl(FactionActions factionActions, PlayerService playerService) {
        this.factionActions = factionActions;
        this.playerService = playerService;
    }

    @Override
    public List<FactionTypes> getRemainingFactions() {
        log.debug("getRemainingFactions");
        return factionActions.getRemainingFactions();
    }

    private void removeSelectedFaction(FactionTypes faction) {
        log.debug("removeSelectedFaction");
        factionActions.removeFromChoice(faction);
    }

    @Override
    public void resetFactionSelection() {
        log.debug("resetFactionSelection");
        factionActions.resetFactionSelection();
    }

    @Override
    public void selectFactionForCurrentPlayer(FactionTypes faction) {
        log.debug("selectFactionForCurrentPlayer");
//        playerService.getCurrentPlayer().setFaction(gameRoom.getFactionActions().getFactionCatalog().factionMap().get(faction));
        removeSelectedFaction(faction);
    }
}
