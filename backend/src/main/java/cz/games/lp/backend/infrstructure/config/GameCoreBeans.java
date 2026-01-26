package cz.games.lp.backend.infrstructure.config;

import cz.games.lp.gamecore.FactionChooser;
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
    public FactionChooser factionChooser() {
        FactionChooser factionChooser = new FactionChooser();
        factionChooser.newGame();
        return factionChooser;
    }
}
