package cz.games.lp.backend.service.agregates;

import cz.games.lp.backend.infrastructure.mapping.GameDataLoader;
import cz.games.lp.backend.infrastructure.mapping.mappers.CardMapper;
import cz.games.lp.backend.infrastructure.mapping.mappers.FactionMapper;

public interface MappingServices {

    GameDataLoader getGameDataLoader();

    CardMapper getCardMapper();

    FactionMapper getFactionMapper();
}
