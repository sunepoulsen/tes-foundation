package dk.sunepoulsen.tes.springboot.rest.logic.configuration;

import org.jspecify.annotations.NullMarked;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class MDCTaskDecorator implements TaskDecorator {

    @NullMarked
    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> context = MDC.getCopyOfContextMap();
        return () -> {
            try {
                if (context != null) MDC.setContextMap(context);
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

}
