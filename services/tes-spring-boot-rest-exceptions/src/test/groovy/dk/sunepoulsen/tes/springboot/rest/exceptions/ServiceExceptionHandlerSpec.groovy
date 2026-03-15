package dk.sunepoulsen.tes.springboot.rest.exceptions


import dk.sunepoulsen.tes.rest.models.ServiceErrorModel
import dk.sunepoulsen.tes.rest.models.ServiceValidationError
import dk.sunepoulsen.tes.rest.models.ServiceValidationErrorModel
import dk.sunepoulsen.tes.springboot.backend.logging.exceptions.RequestHeaderValueException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import spock.lang.Specification

class ServiceExceptionHandlerSpec extends Specification {

    private static final ServiceErrorModel DEFAULT_SERVICE_ERROR_MODEL = new ServiceErrorModel(
        code: 'code',
        param: 'param',
        message: 'message'
    )

    private ValidationErrorsTransformations validationErrorsTransformations
    private ServiceExceptionHandler sut

    void setup() {
        this.validationErrorsTransformations = Mock(ValidationErrorsTransformations)
        this.sut = new ServiceExceptionHandler(this.validationErrorsTransformations)
    }

    void "Exception handling of ApiBadRequestException"() {
        given:
            ApiBadRequestException exception = new ApiBadRequestException(
                DEFAULT_SERVICE_ERROR_MODEL.code,
                DEFAULT_SERVICE_ERROR_MODEL.param,
                DEFAULT_SERVICE_ERROR_MODEL.message
            )

        when:
            ResponseEntity<ServiceErrorModel> response = sut.handleBadRequest(exception)

        then:
            response.statusCode == HttpStatus.BAD_REQUEST
            response.headers.containsHeaderValue(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            response.body == DEFAULT_SERVICE_ERROR_MODEL

            0 * _
    }

    void "Exception handling of RequestHeaderValueException"() {
        given:
            RequestHeaderValueException exception = new RequestHeaderValueException('headerName', 'headerValue', 'message', null)

        when:
            ResponseEntity<ServiceErrorModel> response = sut.handleRequestHeaderValueException(exception)

        then:
            response.statusCode == HttpStatus.BAD_REQUEST
            response.headers.containsHeaderValue(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            response.body == new ServiceErrorModel(
                param: exception.headerName,
                message: exception.message
            )

            0 * _
    }

    void "Exception handling of ApiUnauthorizedException"() {
        given:
            IllegalAccessException accessException = new IllegalAccessException('message')
            ApiUnauthorizedException exception = new ApiUnauthorizedException(accessException)

        when:
            ResponseEntity<ServiceErrorModel> response = sut.handleUnauthorized(exception)

        then:
            response.statusCode == HttpStatus.UNAUTHORIZED
            response.headers.containsHeaderValue(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            response.body == new ServiceErrorModel(
                message: 'Unable to authenticate user'
            )

            0 * _
    }

    void "Exception handling of ApiForbiddenException"() {
        given:
            ApiForbiddenException exception = new ApiForbiddenException(
                DEFAULT_SERVICE_ERROR_MODEL.code,
                DEFAULT_SERVICE_ERROR_MODEL.param,
                DEFAULT_SERVICE_ERROR_MODEL.message
            )

        when:
            ResponseEntity<ServiceErrorModel> response = sut.handleForbidden(exception)

        then:
            response.statusCode == HttpStatus.FORBIDDEN
            response.headers.containsHeaderValue(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            response.body == DEFAULT_SERVICE_ERROR_MODEL

            0 * _
    }

    void "Exception handling of ApiNotFoundException"() {
        given:
            ApiNotFoundException exception = new ApiNotFoundException(
                DEFAULT_SERVICE_ERROR_MODEL.code,
                DEFAULT_SERVICE_ERROR_MODEL.param,
                DEFAULT_SERVICE_ERROR_MODEL.message
            )

        when:
            ResponseEntity<ServiceErrorModel> response = sut.handleNotFound(exception)

        then:
            response.statusCode == HttpStatus.NOT_FOUND
            response.headers.containsHeaderValue(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            response.body == DEFAULT_SERVICE_ERROR_MODEL

            0 * _
    }

    void "Exception handling of ApiConflictException"() {
        given:
            ApiConflictException exception = new ApiConflictException(
                DEFAULT_SERVICE_ERROR_MODEL.code,
                DEFAULT_SERVICE_ERROR_MODEL.param,
                DEFAULT_SERVICE_ERROR_MODEL.message
            )

        when:
            ResponseEntity<ServiceErrorModel> response = sut.handleConflict(exception)

        then:
            response.statusCode == HttpStatus.CONFLICT
            response.headers.containsHeaderValue(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            response.body == DEFAULT_SERVICE_ERROR_MODEL

            0 * _
    }

    void "Exception handling of UnsupportedOperationException"() {
        given:
            UnsupportedOperationException exception = new UnsupportedOperationException('message')

        when:
            ResponseEntity<ServiceErrorModel> response = sut.handleUnsupportedOperation(exception)

        then:
            response.statusCode == HttpStatus.NOT_IMPLEMENTED
            response.headers.containsHeaderValue(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            response.body == new ServiceErrorModel(
                message: 'message'
            )

            0 * _
    }

    void "Exception handling of ApiBadGatewayException"() {
        given:
            ApiBadGatewayException exception = new ApiBadGatewayException(
                DEFAULT_SERVICE_ERROR_MODEL.code,
                DEFAULT_SERVICE_ERROR_MODEL.param,
                DEFAULT_SERVICE_ERROR_MODEL.message
            )

        when:
            ResponseEntity<ServiceErrorModel> response = sut.handleBadGateway(exception)

        then:
            response.statusCode == HttpStatus.BAD_GATEWAY
            response.headers.containsHeaderValue(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            response.body == DEFAULT_SERVICE_ERROR_MODEL

            0 * _
    }

    void "Exception handling of ApiInternalServerException"() {
        when:
            ResponseEntity<ServiceErrorModel> response = sut.handleInternalServerError(new ApiInternalServerException())

        then:
            response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
            response.headers.containsHeaderValue(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            response.body == new ServiceErrorModel(
                message: ApiInternalServerException.EXCEPTION_MESSAGE
            )

            0 * _
    }

    void "Exception handling of ConstraintViolationException"() {
        given:
            ConstraintViolationException arg = Mock(ConstraintViolationException)
            List<ServiceValidationError> expectedValidationErrors = [
                new ServiceValidationError(
                    param: 'max',
                    message: 'must be greater than or equal to 0'
                ),
                new ServiceValidationError(
                    param: 'min',
                    message: 'must be greater than or equal to 0'
                ),
            ]

        when:
            ResponseEntity<ServiceErrorModel> response = sut.handleConstraintViolationException(arg)

        then:
            response.statusCode == HttpStatus.BAD_REQUEST
            response.headers.containsHeaderValue(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            response.body == new ServiceValidationErrorModel(
                message: 'Unable to process request because of validation errors',
                validationErrors: expectedValidationErrors
            )

            1 * validationErrorsTransformations.toServiceValidationErrors(arg) >> expectedValidationErrors
            1 * arg.getMessage() >> 'Exception message'
            0 * _
    }

    void "Exception handling of MethodArgumentNotValidException"() {
        given:
            MethodArgumentNotValidException arg = Mock(MethodArgumentNotValidException)
            List<FieldError> fieldErrors = []
            List<ServiceValidationError> expectedValidationErrors = [
                new ServiceValidationError(
                    param: 'max',
                    message: 'must be greater than or equal to 0'
                ),
                new ServiceValidationError(
                    param: 'min',
                    message: 'must be greater than or equal to 0'
                ),
            ]

        when:
            ResponseEntity<ServiceErrorModel> response = sut.handleMethodArgumentNotValidException(arg)

        then:
            response.statusCode == HttpStatus.BAD_REQUEST
            response.headers.containsHeaderValue(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            response.body == new ServiceValidationErrorModel(
                message: 'Unable to process request because of validation errors',
                validationErrors: expectedValidationErrors
            )

            1 * arg.getFieldErrors() >> fieldErrors
            1 * validationErrorsTransformations.toServiceValidationErrors(fieldErrors) >> expectedValidationErrors
            1 * arg.getMessage() >> 'Exception message'
            0 * _
    }

    void "Exception handling of RuntimeException"() {
        when:
            ResponseEntity<ServiceErrorModel> response = sut.handleRuntimeException(new IllegalArgumentException('message'))

        then:
            response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
            response.headers.containsHeaderValue(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            response.body == new ServiceErrorModel(
                message: ApiInternalServerException.EXCEPTION_MESSAGE
            )

            0 * _
    }

}
