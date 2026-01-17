package cz.games.lp.backend.service;

public interface GameApplicationService {

    void initializeGame();

    void performProductionPhase();

    CardService getCardService();
}
