package dk.sunepoulsen.tes.springboot.rest.logic.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadPoolExecutorFactory {

    public static final Integer DEFAULT_CORE_POOL_SIZE = 8;
    public static final Integer DEFAULT_MAX_POOL_SIZE = Integer.MAX_VALUE;
    public static final Integer DEFAULT_QUEUE_CAPACITY = Integer.MAX_VALUE;

    public static ThreadPoolTaskExecutor createExecutor(ExecutorConfig executorConfig) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(executorValue(executorConfig.getCorePoolSize(), DEFAULT_CORE_POOL_SIZE));
        executor.setMaxPoolSize(executorValue(executorConfig.getMaxPoolSize(), DEFAULT_MAX_POOL_SIZE));
        executor.setQueueCapacity(executorValue(executorConfig.getQueueCapacity(), DEFAULT_QUEUE_CAPACITY));
        executor.setTaskDecorator(new MDCTaskDecorator());

        return executor;
    }

    private static Integer executorValue(final Integer value, final Integer defaultValue) {
        if (value < 0) {
            return defaultValue;
        }

        return value;
    }
}
