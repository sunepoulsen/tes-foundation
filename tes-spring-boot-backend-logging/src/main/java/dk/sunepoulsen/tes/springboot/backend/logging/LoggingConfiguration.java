package dk.sunepoulsen.tes.springboot.backend.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

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
    @Value("${requests.logging.include-query-string:false}")
    private Boolean includeQueryString;

    @Value("${requests.logging.include-headers:false}")
    private Boolean includeHeaders;

    @Value("${requests.logging.include-payload:false}")
    private Boolean includePayload;

    @Value("${requests.logging.max-payload-length:0}")
    private Integer maxPayloadLength;

    @Value("${requests.logging.after-message-prefix:REQUEST DATA: }")
    private String afterMessagePrefix;

    /**
     * Constructs a CommonsRequestLoggingFilter filter for Spring Boot applications.
     *
     * @return The new instance of <code>CommonsRequestLoggingFilter</code> that is ready for use.
     */
    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        log.info("Creating logging configuration.");
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(includeQueryString);
        filter.setIncludePayload(includePayload);
        filter.setMaxPayloadLength(maxPayloadLength);
        filter.setIncludeHeaders(includeHeaders);
        filter.setAfterMessagePrefix(afterMessagePrefix);
        return filter;
    }
}
