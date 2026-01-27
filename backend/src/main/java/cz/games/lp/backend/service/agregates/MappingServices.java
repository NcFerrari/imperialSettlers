package cz.games.lp.backend.service.agregates;

import cz.games.lp.backend.infrstructure.mapping.GameDataLoader;
import cz.games.lp.backend.infrstructure.mapping.mappers.CardMapper;
import cz.games.lp.backend.infrstructure.mapping.mappers.FactionMapper;

public interface MappingServices {

    GameDataLoader getGameDataLoader();

    CardMapper getCardMapper();

    FactionMapper getFactionMapper();
}
