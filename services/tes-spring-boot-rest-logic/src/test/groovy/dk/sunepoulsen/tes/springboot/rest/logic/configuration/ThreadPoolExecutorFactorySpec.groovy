package dk.sunepoulsen.tes.springboot.rest.logic.configuration

import org.slf4j.MDC
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import spock.lang.Specification

import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class ThreadPoolExecutorFactorySpec extends Specification {

    void cleanup() {
        MDC.clear()
    }

    void "Apply configured values and substitute defaults for negative values"() {
        given:
            ExecutorConfig config = new ExecutorConfig(corePoolSize: core, maxPoolSize: max, queueCapacity: queue)

        when:
            ThreadPoolTaskExecutor executor = ThreadPoolExecutorFactory.createExecutor(config)

        then:
            executor.corePoolSize == expectedCore
            executor.maxPoolSize == expectedMax
            executor.queueCapacity == expectedQueue

        where:
            core | max | queue || expectedCore | expectedMax       | expectedQueue
            2    | 5   | 7     || 2            | 5                 | 7
            -1   | 5   | 7     || 8            | 5                 | 7
            2    | -1  | 7     || 2            | Integer.MAX_VALUE | 7
            2    | 5   | -1    || 2            | 5                 | Integer.MAX_VALUE
            0    | 1   | 0     || 0            | 1                 | 0
    }

    void "Propagate MDC context to executor tasks"() {
        given:
            ThreadPoolTaskExecutor executor = ThreadPoolExecutorFactory.createExecutor(new ExecutorConfig())
            executor.initialize()
            MDC.put('request', 'test-request')

        when:
            String observed = executor.submit({ MDC.get('request') } as Callable<String>).get(5, TimeUnit.SECONDS)

        then:
            observed == 'test-request'

        cleanup:
            executor?.shutdown()
    }
}