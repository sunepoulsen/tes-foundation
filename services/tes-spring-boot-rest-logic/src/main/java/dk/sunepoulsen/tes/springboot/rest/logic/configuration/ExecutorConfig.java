package dk.sunepoulsen.tes.springboot.rest.logic.configuration;

import lombok.Data;

@Data
public class ExecutorConfig {

    private int corePoolSize;
    private int maxPoolSize;
    private int queueCapacity;

}
