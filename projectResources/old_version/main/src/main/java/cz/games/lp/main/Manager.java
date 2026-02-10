package cz.games.lp.main;

import cz.games.lp.backend.BackendManager;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.common.game.GameData;
import cz.games.lp.frontend.MainApp;
import cz.games.lp.frontend.api.IManager;

import java.util.Comparator;
import java.util.List;

public class Manager implements IManager {

    private static final int FACTION_CARD_COUNT = 30;
    private static final int COMMON_CARD_COUNT = 84;
    private final BackendManager backendManager = new BackendManager();
    private final GameData gameData;

    public static void main(String[] args) {
        new Manager().start();
    }

    private Manager() {
        backendManager.log(getClass()).info("starting Application...");
        backendManager.prepareCardAndFactionData();
        backendManager.log(getClass()).info("creating new game");
        gameData = new GameData(FACTION_CARD_COUNT, COMMON_CARD_COUNT);
    }

    private void start() {
        MainApp.run(this, gameData);
    }

    @Override
    public CardDTO getCardData(String cardId) {
        return backendManager.getCards().get(cardId);
    }

    @Override
    public FactionDTO getFaction(String faction) {
        return backendManager.getFactions().get(faction);
    }

    @Override
    public List<CardDTO> getMockList() {
        return backendManager.getCards()
                .values()
                .stream()
                .filter(card -> card.getCardType().equals(CardTypes.PRODUCTION) && card.getCardId().startsWith("com"))
                .sorted(Comparator.comparing(CardDTO::getCardId))
                .toList();
    }
}