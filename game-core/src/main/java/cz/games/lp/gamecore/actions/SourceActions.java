package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.components.Player;
import cz.games.lp.gamecore.components.enums.Sources;

import java.util.List;

public record SourceActions(PlayerActions playerActions) {

    public void giveSourcesToPlayer(Player player, List<Sources> sources) {
        sources.forEach(source -> giveSourceToPlayer(player, source));
    }

    private void giveSourceToPlayer(Player player, Sources source) {
        if (Sources.VICTORY_POINT.equals(source)) {
            playerActions.addVictoryPointToPlayer(player);
            return;
        }
        player.getOwnSources().merge(source, 1, Integer::sum);
    }
}
