package dk.sunepoulsen.tes.springboot.rest.logic.configuration

import org.slf4j.MDC
import spock.lang.Specification

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MDCTaskDecoratorSpec extends Specification {

    void cleanup() {
        MDC.clear()
    }

    void "Capture the context at decoration time and clear the worker context afterward"() {
        given:
            ExecutorService executor = Executors.newSingleThreadExecutor()
            Map<String, String> observed = [:]
            MDC.put('request', 'original')
            Runnable task = new MDCTaskDecorator().decorate({ observed.putAll(MDC.getCopyOfContextMap()) } as Runnable)
            MDC.put('request', 'changed')

        when:
            Map<String, String> after = executor.submit({
                MDC.put('worker', 'previous')
                task.run()
                return MDC.getCopyOfContextMap()
            } as Callable<Map<String, String>>).get(5, TimeUnit.SECONDS)

        then:
            observed == [request: 'original']
            !after
            MDC.get('request') == 'changed'

        cleanup:
            executor.shutdownNow()
    }

    void "Clear the worker context even when the task throws and no context was captured"() {
        given:
            ExecutorService executor = Executors.newSingleThreadExecutor()
            MDC.clear()
            RuntimeException failure = new RuntimeException('task failed')
            Map<String, String> observed = [:]
            Runnable task = new MDCTaskDecorator().decorate({
                observed.putAll(MDC.getCopyOfContextMap())
                throw failure
            } as Runnable)

        when:
            List<Object> result = executor.submit({
                MDC.put('worker', 'previous')
                Throwable thrownException = null
                try {
                    task.run()
                } catch (RuntimeException exception) {
                    thrownException = exception
                }
                return [thrownException, MDC.getCopyOfContextMap()]
            } as Callable<List<Object>>).get(5, TimeUnit.SECONDS)

        then:
            observed == [worker: 'previous']
            result[0].is(failure)
            !result[1]

        cleanup:
            executor.shutdownNow()
    }
}