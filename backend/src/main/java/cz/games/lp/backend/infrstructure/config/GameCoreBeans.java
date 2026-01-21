package cz.games.lp.backend.infrstructure.config;

import cz.games.lp.common.enums.FactionTitles;
import cz.games.lp.gamecore.FactionChooser;
import cz.games.lp.gamecore.GameManager;
import cz.games.lp.gamecore.catalogs.CardCatalog;
import cz.games.lp.gamecore.catalogs.FactionCatalog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.HashMap;

@Configuration
public class GameCoreBeans {

    @Bean
    public CardCatalog cardCatalog() {
        return new CardCatalog(new HashMap<>());
    }

    @Bean
    public FactionCatalog factionMap() {
        return new FactionCatalog(new EnumMap<>(FactionTitles.class));
    }

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
