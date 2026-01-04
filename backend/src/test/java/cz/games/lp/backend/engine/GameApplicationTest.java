package cz.games.lp.backend.engine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameApplicationTest {

    @Mock
    private GameEngine gameEngine;

    @Mock
    private ConsoleListener consoleListener;

    @InjectMocks
    private GameApplication gameApplication;

    @Test
    void contextLoadsAndStartsApplication() {
        when(gameEngine.prepareData()).thenReturn(CompletableFuture.completedFuture("ok"));

        gameApplication.startApplication();

        verify(gameEngine, times(1)).prepareData();
        verify(consoleListener, times(1)).startConsoleGame();
    }
}