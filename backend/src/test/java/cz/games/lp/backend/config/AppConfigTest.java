package cz.games.lp.backend.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.Executor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AppConfig.class)
class AppConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void objectMapperBeanTest() {
        ObjectMapper mapper = context.getBean(ObjectMapper.class);
        assertThat(mapper).isNotNull();
    }

    @Test
    void threadExecutorBeanTest() {
        Executor executor = (Executor) context.getBean("thread");
        assertThat(executor).isNotNull().isInstanceOf(org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor.class);

        ThreadPoolTaskExecutor tp = (ThreadPoolTaskExecutor) executor;
        assertThat(tp.getCorePoolSize()).isEqualTo(1);
        assertThat(tp.getMaxPoolSize()).isEqualTo(1);
        assertThat(tp.getQueueCapacity()).isEqualTo(100);
        assertThat(tp.getThreadNamePrefix()).isEqualTo("background-worker-");
    }

    @Test
    void consoleExecutorBeanTest() {
        Executor executor = (Executor) context.getBean("consoleExecutor");
        assertThat(executor).isNotNull().isInstanceOf(org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor.class);

        ThreadPoolTaskExecutor tp = (ThreadPoolTaskExecutor) executor;
        assertThat(tp.getCorePoolSize()).isEqualTo(1);
        assertThat(tp.getMaxPoolSize()).isEqualTo(1);
        assertThat(tp.getThreadNamePrefix()).isEqualTo("console-listener-");
    }
}
