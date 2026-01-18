package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.PhaseService;
import cz.games.lp.common.enums.RoundPhases;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Slf4j
@Service
public class PhaseServiceImpl implements PhaseService {

    private RoundPhases currentPhase;
}
