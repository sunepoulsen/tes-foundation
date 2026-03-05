package dk.sunepoulsen.tes.springboot.rest.logic.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadPoolExecutorFactory {

    public static ThreadPoolTaskExecutor createExecutor(ExecutorConfig executorConfig) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(executorConfig.getCorePoolSize());
        executor.setMaxPoolSize(executorConfig.getMaxPoolSize());
        executor.setQueueCapacity(executorConfig.getQueueCapacity());
        executor.setTaskDecorator(new MDCTaskDecorator());

        return executor;
    }

}
