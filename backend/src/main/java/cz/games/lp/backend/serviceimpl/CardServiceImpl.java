package cz.games.lp.backend.serviceimpl;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.gamecore.service.CardService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CardServiceImpl implements CardService {

    @Getter
    private final Map<String, CardDTO> cardMap = new HashMap<>();

    @Override
    public void deal4Cards() {
        dealFactionCard();
        dealFactionCard();
        dealCommonCard();
        dealCommonCard();
    }

    @Override
    public void dealFactionCard() {

    }

    @Override
    public void dealCommonCard() {

    }
}
