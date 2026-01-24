package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.FactionService;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.FactionTypes;
import cz.games.lp.gamecore.FactionChooser;
import cz.games.lp.gamecore.catalogs.FactionCatalog;
import cz.games.lp.gamecore.components.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FactionServiceImpl implements FactionService {

    private final FactionCatalog factionCatalog;
    private final FactionChooser factionChooser;

    public FactionServiceImpl(FactionCatalog factionCatalog, FactionChooser factionChooser) {
        this.factionCatalog = factionCatalog;
        this.factionChooser = factionChooser;
    }

    @Override
    public Map<FactionTypes, FactionDTO> getFactionMap() {
        log.debug("getFactionMap");
        return factionCatalog.factionMap();
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
        player.selectFaction(getFactionMap().get(faction));
        removeSelectedFaction(faction);
    }

    @Override
    public void resetFactionSelection() {
        log.debug("resetFactionSelection");
        factionChooser.newGame();
    }
}
