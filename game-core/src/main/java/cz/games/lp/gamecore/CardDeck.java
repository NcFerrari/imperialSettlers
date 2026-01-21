package cz.games.lp.gamecore;

import cz.games.lp.common.dto.CardDTO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CardDeck {

    @Getter
    private final List<CardDTO> cards = new ArrayList<>();

}
