package cz.games.lp.backend.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class ConsoleListenerTest {

    private ConsoleListener listener;

    @BeforeEach
    void setUp() {
        listener = new ConsoleListener(runnable -> {
        }, mock(org.springframework.context.ApplicationContext.class));
    }

    @Test
    void cliRunnerTest_exit() {
        String input = "hello\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        try (MockedStatic<SpringApplication> mockedSpring = mockStatic(SpringApplication.class)) {
            mockedSpring.when(() -> SpringApplication.exit(any(), any())).thenReturn(0);
            assertDoesNotThrow(listener::cliRunner);
        }
    }

    @Test
    void cliRunnerTest_interrupted() {
        Thread.currentThread().interrupt();
        assertDoesNotThrow(listener::cliRunner);
    }

    @Test
    void cliRunnerTest_nullInput() {
        String input = "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        assertDoesNotThrow(listener::cliRunner);
    }

    @Test
    void gameInputTest() {
        assertDoesNotThrow(() -> listener.gameInput("hello"));
    }
}