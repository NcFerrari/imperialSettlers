package cz.games.lp.backend.serviceimpl.agregates;

import cz.games.lp.backend.infrstructure.mapping.GameDataLoader;
import cz.games.lp.backend.infrstructure.mapping.mappers.CardMapper;
import cz.games.lp.backend.infrstructure.mapping.mappers.FactionMapper;
import cz.games.lp.backend.service.agregates.MappingService;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class MappingServiceImpl implements MappingService {

    private final CardMapper cardMapper;
    private final FactionMapper factionMapper;
    private final GameDataLoader gameDataLoader;

    public MappingServiceImpl(CardMapper cardMapper, FactionMapper factionMapper, GameDataLoader gameDataLoader) {
        this.cardMapper = cardMapper;
        this.factionMapper = factionMapper;
        this.gameDataLoader = gameDataLoader;
    }
}
