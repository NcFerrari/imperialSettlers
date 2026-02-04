package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.gamecore.actions.CardActions;
import cz.games.lp.gamecore.components.Card;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Getter
@Slf4j
@Service
public class CardServiceImpl implements CardService {

    private final CardActions cardActions = new CardActions();

    @Override
    public Map<String, Card> cardMap() {
        log.debug("cardMap");
        return cardActions.getCardCatalog().cardMap();
    }
}
