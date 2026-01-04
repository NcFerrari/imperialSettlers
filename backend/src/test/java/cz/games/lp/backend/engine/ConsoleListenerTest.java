package cz.games.lp.backend.engine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.Executor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ConsoleListenerTest {

    private Executor executor;
    private ApplicationContext ctx;
    private ConsoleListener listener;
    private InputStream originalIn;

    @BeforeEach
    void setUp() {
        executor = mock(Executor.class);
        ctx = mock(ApplicationContext.class);
        listener = spy(new ConsoleListener(executor, ctx));
        originalIn = System.in;
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
    }

    @Test
    void startConsoleGame_executesCliRunnerTest() {
        listener.startConsoleGame();

        ArgumentCaptor<Runnable> captor = ArgumentCaptor.forClass(Runnable.class);
        verify(executor).execute(captor.capture());

        Runnable runnable = captor.getValue();
        assertThat(runnable).isNotNull();
    }

    @Test
    void cliRunner_readsInput_andProcessesExitTest() {
        String input = "hello\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try (var springMock = mockStatic(SpringApplication.class)) {
            listener.cliRunner();

            verify(listener).gameInput("hello");
            springMock.verify(() ->
                    SpringApplication.exit(eq(ctx), any())
            );
        }
    }

    @Test
    void cliRunner_handlesNullInputTest() {
        System.setIn(new ByteArrayInputStream(new byte[0]));

        listener.cliRunner();

        verify(listener, never()).gameInput(any());
    }

    @Test
    void cliRunner_exceptionInterruptsThreadTest() {
        System.setIn(new InputStream() {
            @Override
            public int read() {
                throw new RuntimeException("boom");
            }
        });

        Thread thread = Thread.currentThread();
        assertThat(thread.isInterrupted()).isFalse();

        listener.cliRunner();

        assertThat(thread.isInterrupted()).isTrue();
    }
}
