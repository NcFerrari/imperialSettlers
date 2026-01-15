package cz.games.lp.backend.serviceimpl;

import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.gamecore.service.SourceService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SourceServiceImpl implements SourceService {

    @Getter
    private final Map<String, CardDTO> cardMap = new HashMap<>();

    public SourceServiceImpl() {
    }
}
