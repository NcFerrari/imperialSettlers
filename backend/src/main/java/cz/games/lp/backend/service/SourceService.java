package cz.games.lp.backend.service;

import cz.games.lp.gamecore.actions.SourceActions;
import cz.games.lp.gamecore.components.Player;
import cz.games.lp.gamecore.components.enums.Sources;

import java.util.List;

public interface SourceService {

    SourceActions getSourceActions();

    void giveSourcesToPlayer(Player player, List<Sources> source);
}
