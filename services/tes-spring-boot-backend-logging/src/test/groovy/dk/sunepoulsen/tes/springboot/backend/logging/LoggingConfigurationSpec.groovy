package dk.sunepoulsen.tes.springboot.backend.logging

import jakarta.servlet.DispatcherType
import jakarta.servlet.FilterRegistration
import jakarta.servlet.ServletContext
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.core.Ordered
import org.springframework.web.servlet.HandlerExceptionResolver
import spock.lang.Specification

class LoggingConfigurationSpec extends Specification {

    void "Register the supplied filter with highest precedence for every dispatcher type"() {
        given:
            HandlerExceptionResolver resolver = Mock(HandlerExceptionResolver)
            LoggingFilter filter = new LoggingFilter(resolver)
            FilterRegistrationBean<LoggingFilter> registration = new LoggingConfiguration().loggingFilterRegistration(filter)
            ServletContext servletContext = Mock(ServletContext)
            FilterRegistration.Dynamic dynamic = Mock(FilterRegistration.Dynamic)

        when:
            registration.onStartup(servletContext)

        then:
            registration.filter.is(filter)
            registration.order == Ordered.HIGHEST_PRECEDENCE
            1 * servletContext.addFilter(_, filter) >> dynamic
            1 * dynamic.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType), false, '/*')
    }
}