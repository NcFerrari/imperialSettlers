package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.backend.service.SourceService;
import cz.games.lp.gamecore.actions.SourceActions;
import cz.games.lp.gamecore.components.Player;
import cz.games.lp.gamecore.components.enums.Sources;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Slf4j
@Service
public class SourceServiceImpl implements SourceService {

    private final SourceActions sourceActions;

    public SourceServiceImpl(PlayerService playerService) {
        this.sourceActions = new SourceActions(playerService.getPlayerActions());
    }

    @Override
    public void giveSourcesToPlayer(Player player, List<Sources> sources) {
        log.debug("giveSourcesToPlayer");
        sourceActions.giveSourcesToPlayer(player, sources);
    }

}
