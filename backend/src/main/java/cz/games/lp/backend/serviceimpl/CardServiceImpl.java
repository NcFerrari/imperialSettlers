package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.gamecore.actions.CardActions;
import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.components.GameRoom;
import cz.games.lp.gamecore.components.Player;
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

    @Override
    public Card getNewPlayerCard(Player player, int cardNumber) {
        log.debug("getNewPlayerCard");
        return cardActions.getNewPlayerCard(player, cardNumber);
    }

    @Override
    public String dealCardToPlayer(Player player, int cardNumber, boolean shuffleRestOfCards) {
        log.debug("dealCardToPlayer");
        return cardActions.dealCardToPlayer(player, cardNumber, shuffleRestOfCards);
    }

    @Override
    public String dealFactionCardToPlayer(Player player) {
        log.debug("dealFactionCardToPlayer");
        return cardActions.dealFactionCard(player);
    }

    @Override
    public String dealCommonCardToPlayer(Player player, GameRoom gameRoom) {
        log.debug("dealCommonCardToPlayer");
        return cardActions.dealCommonCard(player, gameRoom);
    }

    @Override
    public Card getCardByID(String cardID) {
        log.debug("getCardByID");
        return cardActions.getCardByID(cardID);
    }
}
