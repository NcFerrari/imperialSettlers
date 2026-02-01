package cz.games.lp.backend.infrstructure.config;

import cz.games.lp.gamecore.GameRoom;
import cz.games.lp.gamecore.actions.CardActions;
import cz.games.lp.gamecore.actions.FactionActions;
import cz.games.lp.gamecore.actions.ProductionActions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class GameCoreBeans {

    @Bean
    public Map<UUID, GameRoom> gameRooms() {
        return new HashMap<>();
    }

    @Bean
    public ProductionActions productionActions() {
        return new ProductionActions();
    }

    @Bean
    public CardActions cardActions() {
        return new CardActions();
    }

    @Bean
    public FactionActions factionActions() {
        return new FactionActions();
    }
}
