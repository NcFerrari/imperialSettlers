package cz.games.lp.backend.serviceimpl.agregates;

import cz.games.lp.backend.infrstructure.mapping.GameDataLoader;
import cz.games.lp.backend.infrstructure.mapping.mappers.CardMapper;
import cz.games.lp.backend.infrstructure.mapping.mappers.FactionMapper;
import cz.games.lp.backend.service.agregates.MappingServices;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class MappingServicesImpl implements MappingServices {

    private final GameDataLoader gameDataLoader;
    private final CardMapper cardMapper;
    private final FactionMapper factionMapper;

    public MappingServicesImpl(GameDataLoader gameDataLoader, CardMapper cardMapper, FactionMapper factionMapper) {
        this.gameDataLoader = gameDataLoader;
        this.cardMapper = cardMapper;
        this.factionMapper = factionMapper;
    }
}
