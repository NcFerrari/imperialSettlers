package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.FactionService;
import cz.games.lp.backend.service.GameService;
import cz.games.lp.gamecore.components.enums.FactionTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FactionServiceImpl implements FactionService {

    private final GameService gameService;

    public FactionServiceImpl(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public List<FactionTypes> getRemainingFactions(UUID uuid) {
        log.debug("getRemainingFactions");
        return gameService.getGameRoom(uuid).getRemainingFactions();
    }

    @Override
    public void resetFactionSelection(UUID uuid) {
        log.debug("resetFactionSelection");
//        gameService.getGameRoom(uuid).resetFactionSelection();
    }
}
