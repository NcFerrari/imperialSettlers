package cz.games.lp.backend;

import cz.games.lp.backend.engine.GameApplication;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.mockito.Mockito.*;

class ManagerTest {

    @Test
    void runTest() {
        ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);
        GameApplication mockGameApp = mock(GameApplication.class);
        when(mockContext.getBean(GameApplication.class)).thenReturn(mockGameApp);

        try (MockedStatic<org.springframework.boot.SpringApplication> mockedSpring = mockStatic(SpringApplication.class)) {
            mockedSpring.when(() -> SpringApplication.run(Manager.class, new String[]{}))
                    .thenReturn(mockContext);

            Manager manager = new Manager();
            manager.run(new String[]{});

            mockedSpring.verify(() -> SpringApplication.run(Manager.class, new String[]{}));
            verify(mockGameApp).startApplication();
        }
    }

    @Test
    void mainTest() {
        ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);
        GameApplication mockGameApp = mock(GameApplication.class);
        when(mockContext.getBean(GameApplication.class)).thenReturn(mockGameApp);

        try (MockedStatic<SpringApplication> mockedSpring = mockStatic(SpringApplication.class)) {
            mockedSpring.when(() -> SpringApplication.run(Manager.class, new String[]{}))
                    .thenReturn(mockContext);

            Manager.main(new String[]{});

            mockedSpring.verify(() -> SpringApplication.run(Manager.class, new String[]{}));
            verify(mockGameApp).startApplication();
        }
    }
}