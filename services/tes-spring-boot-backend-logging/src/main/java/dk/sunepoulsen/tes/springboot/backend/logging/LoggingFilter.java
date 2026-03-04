package dk.sunepoulsen.tes.springboot.backend.logging;

import dk.sunepoulsen.tes.springboot.backend.logging.exceptions.RequestHeaderValueException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.UUID;

/**
 * LoggingFilter to read Request-Id and add general logging of requests/responses.
 * <p>
 * This filter adds the request header <code>X-Request-ID</code> to the MDC to be used in log patterns with
 * logback or log4j.
 * </p>
 * <table>
 *     <caption>MDC variables</caption>
 *     <tr>
 *         <td><b>Name</b></td>
 *         <td><b>Description</b></td>
 *     </tr>
 *     <tr>
 *         <td>request.id</td>
 *         <td>The id of the request from the <code>X-REQUEST-ID</code> header of the request</td>
 *     </tr>
 * </table>
 */
@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver exceptionHandlerResolver;

    public LoggingFilter(@Qualifier("handlerExceptionResolver")
                         HandlerExceptionResolver exceptionHandlerResolver) {
        this.exceptionHandlerResolver = exceptionHandlerResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterRequest(request);
            filterChain.doFilter(request, response);
            filterResponse(response);
        } catch (RequestHeaderValueException ex) {
            log.info("Starting processing of request: {} {}", request.getMethod().toUpperCase(), request.getRequestURI());
            exceptionHandlerResolver.resolveException(request, response, null, ex);
            filterResponse(response);
        }
    }

    private void filterRequest(HttpServletRequest request) {
        MDC.put(RequestTransaction.OPERATION_ID_MDC_NAME, readOperationIdHeader(request));
        MDC.put(RequestTransaction.TRANSACTION_ID_MDC_NAME, readTransactionIdHeader(request));

        log.info("Starting processing of request: {} {}", request.getMethod().toUpperCase(), request.getRequestURI());
    }

    private void filterResponse(HttpServletResponse response) {
        log.info("Response code: {}", response.getStatus());
        log.info("Done processing request");
    }

    private String readOperationIdHeader(HttpServletRequest request) {
        String headerValue = request.getHeader(RequestTransaction.OPERATION_ID_HEADER_NAME);
        if (headerValue == null) {
            return UUID.randomUUID().toString();
        }

        try {
            return UUID.fromString(headerValue).toString();
        } catch (IllegalArgumentException ex) {
            throw new RequestHeaderValueException(RequestTransaction.OPERATION_ID_HEADER_NAME, headerValue,
                "Request header is not an valid UUID", ex);
        }
    }

    private String readTransactionIdHeader(HttpServletRequest request) {
        String headerValue = request.getHeader(RequestTransaction.TRANSACTION_ID_HEADER_NAME);
        if (headerValue == null) {
            return UUID.randomUUID().toString();
        }

        return headerValue;
    }

}
