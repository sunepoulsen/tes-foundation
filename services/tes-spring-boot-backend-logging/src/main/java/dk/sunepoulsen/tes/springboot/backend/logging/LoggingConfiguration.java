package dk.sunepoulsen.tes.springboot.backend.logging;

import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.EnumSet;

/**
 * Configuration class to configure and activate the request logging from Spring Framework.
 * <p>
 *     This implementation defines a <code>@Bean</code> of <a href="https://docs.spring.io/spring-framework/docs/5.3.23/javadoc-api/org/springframework/web/filter/AbstractRequestLoggingFilter.html">CommonsRequestLoggingFilter</a>
 * </p>
 * <p>
 *     This bean is configured thought application properties.
 * </p>
 *
 * @since 1.1.0
 */
@Configuration
@Slf4j
public class LoggingConfiguration {

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilterRegistration(LoggingFilter filter) {
        FilterRegistrationBean<LoggingFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class)); // includes ERROR
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

}
