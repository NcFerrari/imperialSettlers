package cz.games.lp.backend.engine.consolegame;

import cz.games.lp.backend.engine.GameEngine;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.Factions;
import cz.games.lp.gamecore.service.GameDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConsoleListenerTest {

    @Mock
    private Executor executor;

    @Mock
    private ApplicationContext ctx;

    @Mock
    private ConsoleOutputs outputs;

    @Mock
    private GameDataService gameDataService;

    private GameEngine gameService;
    private Console consoleListener;

    private Method selectFactionMethod;

    @BeforeEach
    void setUp() throws Exception {
//        gameService = new GameEngine(gameEngine, gameDataService);
//        consoleListener = new ConsoleListener(executor, ctx, outputss, gameService);
        selectFactionMethod = Console.class
                .getDeclaredMethod("selectFaction", String.class);
        selectFactionMethod.setAccessible(true);
    }

    @Test
    void startConsoleGame_shouldInitializeAndStartExecutor() {
//        consoleListener.startConsoleGame();

        verify(outputs).initMessage();
        verify(outputs).selectFactionMessage();
        verify(executor).execute(any(Runnable.class));
    }

    @Test
    void selectFaction_validInput_shouldStartNewGameAndShowStats() throws Exception {
        Map<String, FactionDTO> factionMap = new HashMap<>();
        factionMap.put(Factions.BARBARIAN_F.name(), new FactionDTO());

//        when(gameService.getGameEngine().getFactionMap()).thenReturn(factionMap);

        setGameOperation(GameOperations.SELECT_FACTION);

        selectFactionMethod.invoke(consoleListener, "1");

        verify(gameService.getGameDataService()).selectFaction(any());
//        verify(gameService.getGameManagerService()).newGame();
        verify(outputs).showCurrentStats();
        verify(outputs, never()).wrongChoice();
    }

    @Test
    void selectFaction_invalidInput_shouldShowErrorAndRepeatChoice() throws Exception {
        setGameOperation(GameOperations.SELECT_FACTION);

        selectFactionMethod.invoke(consoleListener, "invalid");

        verify(outputs).wrongChoice();
        verify(outputs).selectFactionMessage();
//        verify(gameService.getGameManagerService(), never()).newGame();
        verify(outputs, never()).showCurrentStats();
    }

    @Test
    void selectFaction_outOfRangeInput_shouldBeHandledAsInvalid() throws Exception {
        setGameOperation(GameOperations.OFFER);
        selectFactionMethod.invoke(consoleListener, "99");

        verify(outputs).wrongChoice();
        verify(outputs).selectFactionMessage();
//        verify(gameService.getGameManagerService(), never()).newGame();
    }

    @Test
    void cliRunner_exitCommand_shouldExitApplication() {
        String input = "exit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try (MockedStatic<SpringApplication> springMock = mockStatic(SpringApplication.class)) {
//            consoleListener.cliRunner();
            springMock.verify(() -> SpringApplication.exit(eq(ctx), any()));
        }
    }

    @Test
    void cliRunner_nonExitCommand_shouldCallGameMethod() {
        String input = "1\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Map<String, FactionDTO> factionMap = new HashMap<>();
        factionMap.put(Factions.BARBARIAN_M.name(), new FactionDTO());
//        when(gameService.getGameEngine().getFactionMap()).thenReturn(factionMap);

//        consoleListener.startConsoleGame();

        try (MockedStatic<SpringApplication> springMock = mockStatic(SpringApplication.class)) {
//            consoleListener.cliRunner();

            verify(gameService.getGameDataService()).selectFaction(any());
//            verify(gameService.getGameManagerService()).newGame();
            verify(outputs).showCurrentStats();

            springMock.verify(() -> SpringApplication.exit(eq(ctx), any()));
        }
    }

    @Test
    void cliRunner_showStatsOperation_shouldCallOutputsShowStatsOnly() {
        String input = "1\nanything\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
//        consoleListener.startConsoleGame();

        try (MockedStatic<SpringApplication> springMock = mockStatic(SpringApplication.class)) {
//            consoleListener.cliRunner();
            verify(outputs, atLeastOnce()).showCurrentStats();
            springMock.verify(() -> SpringApplication.exit(eq(ctx), any()));
        }
    }

    /**
     * Pomocná metoda pro nastavení private pole gameOperation
     */
    private void setGameOperation(GameOperations operation) throws Exception {
        var field = Console.class.getDeclaredField("gameOperation");
        field.setAccessible(true);
        field.set(consoleListener, operation);
    }
}
