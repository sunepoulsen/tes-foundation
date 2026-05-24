package dk.sunepoulsen.tes.springboot.rest.logic.configuration;

import lombok.Data;

@Data
public class ExecutorConfig {

    private int corePoolSize = 8;
    private int maxPoolSize = Integer.MAX_VALUE;
    private int queueCapacity = Integer.MAX_VALUE;

}
