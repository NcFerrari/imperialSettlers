package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.common.dto.CardDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CardServiceImpl implements CardService {

    @Getter
    private final Map<String, CardDTO> cardMap = new HashMap<>();
}
