package dk.sunepoulsen.tes.springboot.backend.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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
    private static final String HEADER_REQUEST_ID_NAME = "X-Request-ID";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterRequest(request);
        filterChain.doFilter(request, response);
        filterResponse(response);
    }

    private void filterRequest(HttpServletRequest request) {
        String requestId = request.getHeader(HEADER_REQUEST_ID_NAME);
        if (requestId == null || requestId.isEmpty() || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }
        MDC.put("request.id", requestId);

        log.info("Starting processing of request: {} {}", request.getMethod().toUpperCase(), request.getRequestURI());
    }

    private void filterResponse(HttpServletResponse response) {
        log.info("Response code: {}", response.getStatus());
        log.info("Done processing request");
    }
}
