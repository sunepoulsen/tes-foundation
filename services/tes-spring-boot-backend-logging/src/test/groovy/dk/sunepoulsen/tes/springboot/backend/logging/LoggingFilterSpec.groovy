package dk.sunepoulsen.tes.springboot.backend.logging


import dk.sunepoulsen.tes.springboot.backend.logging.exceptions.RequestHeaderValueException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import spock.lang.Specification

class LoggingFilterSpec extends Specification {

    private LoggingFilter sut

    void setup() {
        this.sut = new LoggingFilter()

        MDC.clear()
    }

    void "Test filter request with missing X-OPERATION-ID header"() {
        given:
            HttpServletRequest request = Mock(HttpServletRequest)

        when:
            sut.filterRequest(request)

        then:
            UUID.fromString(MDC.get(RequestTransaction.OPERATION_ID_MDC_NAME))

            1 * request.getRequestURI() >> '/'
            1 * request.getHeader(RequestTransaction.OPERATION_ID_HEADER_NAME) >> null
            1 * request.getHeader(RequestTransaction.TRANSACTION_ID_HEADER_NAME) >> null
            1 * request.getMethod() >> 'GET'
    }

    void "Test filter request with invalid X-OPERATION-ID header"() {
        given:
            HttpServletRequest request = Mock(HttpServletRequest)

        when:
            sut.filterRequest(request)

        then:
            RequestHeaderValueException exception = thrown(RequestHeaderValueException)
            exception.headerName == RequestTransaction.OPERATION_ID_HEADER_NAME
            exception.headerValue == 'bad-value'
            MDC.getCopyOfContextMap() == null

            1 * request.getHeader(RequestTransaction.OPERATION_ID_HEADER_NAME) >> 'bad-value'
            0 * request.getHeader(RequestTransaction.TRANSACTION_ID_HEADER_NAME)
    }

    void "Test filter request with missing X-TRANSACTION-ID header"() {
        given:
            HttpServletRequest request = Mock(HttpServletRequest)
            UUID operationId = UUID.randomUUID()

        when:
            sut.filterRequest(request)

        then:
            MDC.get(RequestTransaction.OPERATION_ID_MDC_NAME) == operationId.toString()
            UUID.fromString(MDC.get(RequestTransaction.TRANSACTION_ID_MDC_NAME))

            1 * request.getRequestURI() >> '/'
            1 * request.getHeader(RequestTransaction.OPERATION_ID_HEADER_NAME) >> operationId.toString()
            1 * request.getHeader(RequestTransaction.TRANSACTION_ID_HEADER_NAME) >> null
            1 * request.getMethod() >> 'GET'
    }

    void "Test filter request with all supported headers"() {
        given:
            HttpServletRequest request = Mock(HttpServletRequest)
            UUID operationId = UUID.randomUUID()

        when:
            sut.filterRequest(request)

        then:
            MDC.get(RequestTransaction.OPERATION_ID_MDC_NAME) == operationId.toString()
            MDC.get(RequestTransaction.TRANSACTION_ID_MDC_NAME) == 'transaction-id'

            1 * request.getRequestURI() >> '/'
            1 * request.getHeader(RequestTransaction.OPERATION_ID_HEADER_NAME) >> operationId.toString()
            1 * request.getHeader(RequestTransaction.TRANSACTION_ID_HEADER_NAME) >> 'transaction-id'
            1 * request.getMethod() >> 'GET'
    }

    void "Test filter response"() {
        given:
            HttpServletResponse response = Mock(HttpServletResponse)

        and:
            MDC.put(RequestTransaction.OPERATION_ID_MDC_NAME, 'operation-id')
            MDC.put(RequestTransaction.TRANSACTION_ID_MDC_NAME, 'transaction-id')

        when:
            sut.filterResponse(response)

        then:
            MDC.get(RequestTransaction.OPERATION_ID_MDC_NAME) != null
            MDC.get(RequestTransaction.TRANSACTION_ID_MDC_NAME) != null
    }

}
