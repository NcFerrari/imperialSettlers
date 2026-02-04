package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.FactionService;
import cz.games.lp.gamecore.actions.FactionActions;
import cz.games.lp.gamecore.components.Faction;
import cz.games.lp.gamecore.components.enums.FactionTypes;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Getter
@Slf4j
@Service
public class FactionServiceImpl implements FactionService {

    private final FactionActions factionActions = new FactionActions();

    @Override
    public Map<FactionTypes, Faction> factionMap() {
        log.debug("factionMap");
        return factionActions.getFactionCatalog().factionMap();
    }

    @Override
    public void resetFactionSelection(List<FactionTypes> remainingFactions) {
        log.debug("resetFactionSelection");
        factionActions.resetFactionSelection(remainingFactions);
    }

    @Override
    public void removeFromChoice(List<FactionTypes> remainingFactions, FactionTypes factionType) {
        log.debug("removeFromChoice");
        factionActions.removeFromChoice(remainingFactions, factionType);
    }
}
