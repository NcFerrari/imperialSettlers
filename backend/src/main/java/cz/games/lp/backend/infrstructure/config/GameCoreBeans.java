package cz.games.lp.backend.infrstructure.config;

import cz.games.lp.gamecore.actions.CardActions;
import cz.games.lp.gamecore.actions.FactionActions;
import cz.games.lp.gamecore.GameManager;
import cz.games.lp.gamecore.actions.ProductionActions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameCoreBeans {

    @Bean
    public GameManager gameManager() {
        return new GameManager();
    }

    @Bean
    public ProductionActions productionActions() {
        return new ProductionActions();
    }

    @Bean
    public CardActions cardActions() {
        return new CardActions(gameManager().getCommonCardDeckCount(), gameManager());
    }

    @Bean
    public FactionActions factionActions() {
        FactionActions factionActions = new FactionActions();
        factionActions.resetFactionSelection();
        return factionActions;
    }
}
