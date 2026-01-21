package cz.games.lp.backend.service.agregates;

import cz.games.lp.backend.infrstructure.mapping.GameDataLoader;
import cz.games.lp.backend.infrstructure.mapping.mappers.CardMapper;
import cz.games.lp.backend.infrstructure.mapping.mappers.FactionMapper;

public interface MappingService {

    CardMapper getCardMapper();

    FactionMapper getFactionMapper();

    GameDataLoader getGameDataLoader();
}
