package dk.sunepoulsen.tes.springboot.rest.logic.configuration;

import lombok.Data;

/** Configuration values for a task executor's pool and queue. */
@Data
public class ExecutorConfig {

    /** Core number of threads. */
    private int corePoolSize = 8;
    /** Maximum number of threads. */
    private int maxPoolSize = Integer.MAX_VALUE;
    /** Capacity of the task queue. */
    private int queueCapacity = Integer.MAX_VALUE;

}
