package cz.games.lp.backend.engine;

import cz.games.lp.backend.engine.consolegame.Console;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameApplicationTest {

//    @Mock
//    private SourceInit gameEngine;

    @Mock
    private Console consoleListener;

    @InjectMocks
    private GameApplication gameApplication;

    @Test
    void startApplicationTest() {
//        when(gameEngine.prepareData()).thenReturn(CompletableFuture.completedFuture("ok"));
//
//        gameApplication.startApplication();
//
//        verify(gameEngine, times(1)).prepareData();
//        verify(consoleListener, times(1)).startConsoleGame();
    }
}