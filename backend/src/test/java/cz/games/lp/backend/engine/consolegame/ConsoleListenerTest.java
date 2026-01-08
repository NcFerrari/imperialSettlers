package cz.games.lp.backend.engine.consolegame;

import cz.games.lp.backend.engine.GameEngine;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.Factions;
import cz.games.lp.gamecore.service.GameDataService;
import cz.games.lp.gamecore.service.GameManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsoleListenerTest {

    @Mock
    private Executor executor;

    @Mock
    private Outputs outputs;

    @Mock
    private ApplicationContext ctx;

    @Mock
    private GameEngine gameEngine;

    @Mock
    private GameDataService gameDataService;

    @Mock
    private GameManagerService gameManagerService;

    @InjectMocks
    private ConsoleListener consoleListener;

    private Method selectFactionMethod;

    @BeforeEach
    void setUp() throws Exception {
        selectFactionMethod = ConsoleListener.class
                .getDeclaredMethod("selectFaction", String.class);
        selectFactionMethod.setAccessible(true);
    }

    @Test
    void startConsoleGame_shouldInitializeAndStartExecutor() {
        consoleListener.startConsoleGame();

        verify(outputs).initMessage();
        verify(outputs).selectFactionMessage();
        verify(executor).execute(any(Runnable.class));
    }

    @Test
    void selectFaction_validInput_shouldStartNewGameAndShowStats() throws Exception {
        Map<String, FactionDTO> factionMap = new HashMap<>();
        factionMap.put(Factions.BARBARIAN_F.name(), new FactionDTO());

        when(gameEngine.getFactionMap()).thenReturn(factionMap);

        setGameOperation(GameOperations.SELECT_FACTION);

        selectFactionMethod.invoke(consoleListener, "1");

        verify(gameDataService).selectFaction(any());
        verify(gameManagerService).newGame();
        verify(outputs).showStats();
        verify(outputs, never()).wrongChoice();
    }

    @Test
    void selectFaction_invalidInput_shouldShowErrorAndRepeatChoice() throws Exception {
        setGameOperation(GameOperations.SELECT_FACTION);

        selectFactionMethod.invoke(consoleListener, "invalid");

        verify(outputs).wrongChoice();
        verify(outputs).selectFactionMessage();
        verify(gameManagerService, never()).newGame();
        verify(outputs, never()).showStats();
    }

    @Test
    void selectFaction_outOfRangeInput_shouldBeHandledAsInvalid() throws Exception {
        setGameOperation(GameOperations.SHOW_STATS);
        selectFactionMethod.invoke(consoleListener, "99");

        verify(outputs).wrongChoice();
        verify(outputs).selectFactionMessage();
        verify(gameManagerService, never()).newGame();
    }

    @Test
    void cliRunner_exitCommand_shouldExitApplication() {
        String input = "exit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try (MockedStatic<SpringApplication> springMock = mockStatic(SpringApplication.class)) {
            consoleListener.cliRunner();
            springMock.verify(() -> SpringApplication.exit(eq(ctx), any()));
        }
    }

    @Test
    void cliRunner_nonExitCommand_shouldCallGameMethod() {
        String input = "1\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Map<String, FactionDTO> factionMap = new HashMap<>();
        factionMap.put(Factions.BARBARIAN_M.name(), new FactionDTO());
        when(gameEngine.getFactionMap()).thenReturn(factionMap);

        consoleListener.startConsoleGame();

        try (MockedStatic<SpringApplication> springMock = mockStatic(SpringApplication.class)) {
            consoleListener.cliRunner();

            verify(gameDataService).selectFaction(any());
            verify(gameManagerService).newGame();
            verify(outputs).showStats();

            springMock.verify(() -> SpringApplication.exit(eq(ctx), any()));
        }
    }

    @Test
    void cliRunner_showStatsOperation_shouldCallOutputsShowStatsOnly() {
        String input = "1\nanything\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        consoleListener.startConsoleGame();

        try (MockedStatic<SpringApplication> springMock = mockStatic(SpringApplication.class)) {
            consoleListener.cliRunner();
            verify(outputs, atLeastOnce()).showStats();
            springMock.verify(() -> SpringApplication.exit(eq(ctx), any()));
        }
    }

    /**
     * Pomocná metoda pro nastavení private pole gameOperation
     */
    private void setGameOperation(GameOperations operation) throws Exception {
        var field = ConsoleListener.class.getDeclaredField("gameOperation");
        field.setAccessible(true);
        field.set(consoleListener, operation);
    }
}
