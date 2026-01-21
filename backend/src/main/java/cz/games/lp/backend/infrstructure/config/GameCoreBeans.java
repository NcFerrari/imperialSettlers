package cz.games.lp.backend.infrstructure.config;

import cz.games.lp.common.enums.FactionTitles;
import cz.games.lp.gamecore.catalogs.CardCatalog;
import cz.games.lp.gamecore.catalogs.FactionCatalog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.HashMap;

@Configuration
public class GameCoreBeans {

    @Bean
    public CardCatalog getCardCatalog() {
        return new CardCatalog(new HashMap<>());
    }

    @Bean
    public FactionCatalog getFactionCatalog() {
        return new FactionCatalog(new EnumMap<>(FactionTitles.class));
    }
}
