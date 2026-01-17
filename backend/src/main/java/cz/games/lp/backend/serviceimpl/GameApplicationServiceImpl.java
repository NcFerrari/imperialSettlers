package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.GameApplicationService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GameApplicationServiceImpl implements GameApplicationService {

    @Getter
    private final CardService cardService;

    public GameApplicationServiceImpl(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public void initializeGame() {

    }

    @Override
    public void performProductionPhase() {

    }
}
