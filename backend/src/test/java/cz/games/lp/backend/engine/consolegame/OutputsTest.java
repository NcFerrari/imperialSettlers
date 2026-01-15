package cz.games.lp.backend.engine.consolegame;

import cz.games.lp.backend.serviceimpl.GameDataServiceImpl;
import cz.games.lp.gamecore.GameData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OutputsTest {

    @Mock
    private GameDataServiceImpl gameDataService;

    @Mock
    private GameData gameData;

    @InjectMocks
    private ConsoleOutputs outputs;

    @Test
    void runAllOutputMethods() {
        when(gameDataService.getGameData()).thenReturn(gameData);
        outputs.initMessage();
        outputs.selectFactionMessage();
        outputs.wrongChoice();
        outputs.showCurrentStats();
        assertTrue(true);
    }
}
