package cz.games.lp.backend.infrstructure.config;

import cz.games.lp.gamecore.CardManager;
import cz.games.lp.gamecore.FactionManager;
import cz.games.lp.gamecore.GameManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameCoreBeans {

    @Bean
    public GameManager gameManager() {
        return new GameManager();
    }

    @Bean
    public CardManager cardManager() {
        return gameManager().getCardManager();
    }

    @Bean
    public FactionManager factionChooser() {
        FactionManager factionManager = new FactionManager();
        factionManager.resetFactionSelection();
        return factionManager;
    }
}
