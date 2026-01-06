package cz.games.lp.backend.serviceimpl;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.gamecore.service.GameDataService;
import cz.games.lp.gamecore.service.UserInputService;
import org.springframework.stereotype.Service;

@Service
public class UserInputServiceImpl implements UserInputService {

    private final GameDataService gameDataService;

    public UserInputServiceImpl(GameDataService gameDataService) {
        this.gameDataService = gameDataService;
    }

    @Override
    public void selectFaction(FactionDTO faction) {
        gameDataService.getGameData().setSelectedFaction(faction);
    }
}
