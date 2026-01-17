package cz.games.lp.backend.engine;

import cz.games.lp.gamecore.GameData;
import cz.games.lp.gamecore.service.CardService;
import cz.games.lp.gamecore.service.FactionService;
import cz.games.lp.gamecore.service.GameDataService;
import cz.games.lp.gamecore.service.PhaseService;
import cz.games.lp.gamecore.service.SourceService;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GameServices {

    private final GameDataService gameDataService;
    private final CardService cardService;
    private final FactionService factionService;
    private final PhaseService phaseService;
    private final SourceService sourceService;

    public GameServices(GameDataService gameDataService, CardService cardService, FactionService factionService, PhaseService phaseService, SourceService sourceService) {
        this.gameDataService = gameDataService;
        this.cardService = cardService;
        this.factionService = factionService;
        this.phaseService = phaseService;
        this.sourceService = sourceService;
    }

    public GameData getGameData() {
        return gameDataService.getGameData();
    }
}
