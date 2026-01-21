package cz.games.lp.backend.serviceimpl.agregates;

import cz.games.lp.backend.service.agregates.GameCoreService;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.FactionTitles;
import cz.games.lp.gamecore.catalogs.CardCatalog;
import cz.games.lp.gamecore.catalogs.FactionCatalog;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GameCoreServiceImpl implements GameCoreService {

    private final CardCatalog cardCatalog;
    private final FactionCatalog factionCatalog;

    public GameCoreServiceImpl(CardCatalog cardCatalog, FactionCatalog factionCatalog) {
        this.cardCatalog = cardCatalog;
        this.factionCatalog = factionCatalog;
    }

    @Override
    public Map<String, CardDTO> getCardMap() {
        return cardCatalog.cardMap();
    }

    @Override
    public Map<FactionTitles, FactionDTO> getFactionMap() {
        return factionCatalog.factionMap();
    }
}
