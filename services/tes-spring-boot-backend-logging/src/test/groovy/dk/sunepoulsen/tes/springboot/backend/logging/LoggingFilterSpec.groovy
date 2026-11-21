package dk.sunepoulsen.tes.springboot.backend.logging


import dk.sunepoulsen.tes.springboot.backend.logging.exceptions.RequestHeaderValueException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.web.servlet.HandlerExceptionResolver
import spock.lang.Specification

class LoggingFilterSpec extends Specification {

    private LoggingFilter sut
    private HandlerExceptionResolver resolver
    private HttpServletRequest request
    private HttpServletResponse response
    private FilterChain chain

    void setup() {
        this.resolver = Mock(HandlerExceptionResolver)
        this.request = Mock(HttpServletRequest)
        this.response = Mock(HttpServletResponse)
        this.chain = Mock(FilterChain)
        this.sut = new LoggingFilter(resolver)

        MDC.clear()
    }

    void cleanup() {
        MDC.clear()
    }

    void "Test filter request with missing X-OPERATION-ID header"() {
        when:
            sut.doFilterInternal(request, response, chain)

        then:
            UUID.fromString(MDC.get(RequestTransaction.OPERATION_ID_MDC_NAME))

            1 * request.getRequestURI() >> '/'
            1 * request.getHeader(RequestTransaction.OPERATION_ID_HEADER_NAME) >> null
            1 * request.getHeader(RequestTransaction.TRANSACTION_ID_HEADER_NAME) >> null
            1 * request.getMethod() >> 'GET'
            1 * chain.doFilter(request, response)
            1 * response.getStatus()
            0 * resolver.resolveException(_, _, _, _)
    }

    void "Test filter request with invalid X-OPERATION-ID header"() {
        when:
            sut.doFilterInternal(request, response, chain)

        then:
            MDC.getCopyOfContextMap() == null

            1 * request.getHeader(RequestTransaction.OPERATION_ID_HEADER_NAME) >> 'bad-value'
            0 * request.getHeader(RequestTransaction.TRANSACTION_ID_HEADER_NAME)
            1 * request.getMethod() >> 'GET'
            1 * request.getRequestURI() >> '/'
            0 * chain.doFilter(_, _)
            1 * resolver.resolveException(request, response, null, { RequestHeaderValueException exception ->
                exception.headerName == RequestTransaction.OPERATION_ID_HEADER_NAME && exception.headerValue == 'bad-value'
            })
            1 * response.getStatus()
    }

    void "Test filter request with missing X-TRANSACTION-ID header"() {
        given:
            UUID operationId = UUID.randomUUID()

        when:
            sut.doFilterInternal(request, response, chain)

        then:
            MDC.get(RequestTransaction.OPERATION_ID_MDC_NAME) == operationId.toString()
            UUID.fromString(MDC.get(RequestTransaction.TRANSACTION_ID_MDC_NAME))

            1 * request.getRequestURI() >> '/'
            1 * request.getHeader(RequestTransaction.OPERATION_ID_HEADER_NAME) >> operationId.toString()
            1 * request.getHeader(RequestTransaction.TRANSACTION_ID_HEADER_NAME) >> null
            1 * request.getMethod() >> 'GET'
            1 * chain.doFilter(request, response)
            1 * response.getStatus()
            0 * resolver.resolveException(_, _, _, _)
    }

    void "Test filter request with all supported headers"() {
        given:
            UUID operationId = UUID.randomUUID()

        when:
            sut.doFilterInternal(request, response, chain)

        then:
            MDC.get(RequestTransaction.OPERATION_ID_MDC_NAME) == operationId.toString()
            MDC.get(RequestTransaction.TRANSACTION_ID_MDC_NAME) == 'transaction-id'

            1 * request.getRequestURI() >> '/'
            1 * request.getHeader(RequestTransaction.OPERATION_ID_HEADER_NAME) >> operationId.toString()
            1 * request.getHeader(RequestTransaction.TRANSACTION_ID_HEADER_NAME) >> 'transaction-id'
            1 * request.getMethod() >> 'GET'
            1 * chain.doFilter(request, response)
            1 * response.getStatus()
            0 * resolver.resolveException(_, _, _, _)
    }

    void "Test downstream exception is resolved and response is logged"() {
        given:
            RuntimeException exception = new RuntimeException('downstream failure')

        when:
            sut.doFilterInternal(request, response, chain)

        then:
            MDC.get(RequestTransaction.OPERATION_ID_MDC_NAME) != null
            MDC.get(RequestTransaction.TRANSACTION_ID_MDC_NAME) != null
            1 * request.getHeader(RequestTransaction.OPERATION_ID_HEADER_NAME) >> null
            1 * request.getHeader(RequestTransaction.TRANSACTION_ID_HEADER_NAME) >> null
            1 * request.getMethod() >> 'GET'
            1 * request.getRequestURI() >> '/'
            1 * chain.doFilter(request, response) >> { throw exception }
            1 * resolver.resolveException(request, response, null, exception)
            1 * response.getStatus()
    }

}
