package dk.sunepoulsen.tes.springboot.rest.exceptions;

import dk.sunepoulsen.tes.rest.models.ServiceErrorModel;
import dk.sunepoulsen.tes.rest.models.ServiceValidationErrorModel;
import dk.sunepoulsen.tes.springboot.backend.logging.exceptions.RequestHeaderValueException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ServiceExceptionHandler {

    private final ValidationErrorsTransformations validationErrorsTransformations;

    public ServiceExceptionHandler() {
        this(new ValidationErrorsTransformations());
    }

    public ServiceExceptionHandler(ValidationErrorsTransformations validationErrorsTransformations) {
        this.validationErrorsTransformations = validationErrorsTransformations;
    }

    @Hidden
    @ExceptionHandler(ApiBadRequestException.class)
    @ResponseBody
    public ResponseEntity<ServiceErrorModel> handleBadRequest(ApiBadRequestException ex) {
        return ResponseEntity
            .badRequest()
            .contentType(MediaType.APPLICATION_JSON)
            .body(processCheckedException(ex));
    }

    @Hidden
    @ExceptionHandler(RequestHeaderValueException.class)
    @ResponseBody
    public ResponseEntity<ServiceErrorModel> handleRequestHeaderValueException(RequestHeaderValueException ex) {
        ServiceErrorModel model = new ServiceErrorModel();
        model.setParam(ex.getHeaderName());
        model.setMessage(ex.getMessage());

        completeHandlingOfException(ex, model);

        return ResponseEntity
            .badRequest()
            .contentType(MediaType.APPLICATION_JSON)
            .body(model);
    }

    @Hidden
    @ExceptionHandler(ApiUnauthorizedException.class)
    @ResponseBody
    public ResponseEntity<ServiceErrorModel> handleUnauthorized(ApiUnauthorizedException ex) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(processCheckedException(ex));
    }

    @Hidden
    @ExceptionHandler(ApiForbiddenException.class)
    @ResponseBody
    public ResponseEntity<ServiceErrorModel> handleForbidden(ApiForbiddenException ex) {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .contentType(MediaType.APPLICATION_JSON)
            .body(processCheckedException(ex));
    }

    @Hidden
    @ExceptionHandler(ApiNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ServiceErrorModel> handleNotFound(ApiNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body(processCheckedException(ex));
    }

    @Hidden
    @ExceptionHandler(ApiConflictException.class)
    @ResponseBody
    public ResponseEntity<ServiceErrorModel> handleConflict(ApiConflictException ex) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .contentType(MediaType.APPLICATION_JSON)
            .body(processCheckedException(ex));
    }

    @Hidden
    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseBody
    public ResponseEntity<ServiceErrorModel> handleUnsupportedOperation(UnsupportedOperationException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_IMPLEMENTED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(processCheckedException(ex));
    }

    @Hidden
    @ExceptionHandler(ApiBadGatewayException.class)
    @ResponseBody
    public ResponseEntity<ServiceErrorModel> handleBadGateway(ApiBadGatewayException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_GATEWAY)
            .contentType(MediaType.APPLICATION_JSON)
            .body(processCheckedException(ex));
    }

    @Hidden
    @ExceptionHandler(ApiInternalServerException.class)
    @ResponseBody
    public ResponseEntity<ServiceErrorModel> handleInternalServerError(ApiInternalServerException ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(processCheckedException(ex));
    }

    @Hidden
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ServiceValidationErrorModel> handleConstraintViolationException(ConstraintViolationException ex) {
        ServiceValidationErrorModel model = new ServiceValidationErrorModel();

        try {
            model.setMessage("Unable to process request because of validation errors");
            model.setValidationErrors(validationErrorsTransformations.toServiceValidationErrors(ex));

            return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(model);
        } finally {
            completeHandlingOfException(ex, model);
        }
    }

    @Hidden
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ServiceValidationErrorModel> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ServiceValidationErrorModel model = new ServiceValidationErrorModel();

        try {
            model.setMessage("Unable to process request because of validation errors");
            model.setValidationErrors(validationErrorsTransformations.toServiceValidationErrors(ex.getFieldErrors()));

            return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(model);
        } finally {
            completeHandlingOfException(ex, model);
        }
    }

    @Hidden
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ServiceErrorModel> handleRuntimeException(RuntimeException ex) {
        ServiceErrorModel body = null;
        try {
            body = new ServiceErrorModel();
            body.setMessage(ApiInternalServerException.EXCEPTION_MESSAGE);

            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return null;
        } finally {
            log.info(ex.getMessage(), ex);
            logResponseBody(body);
        }
    }

    private ServiceErrorModel processCheckedException(Exception ex) {
        ServiceErrorModel body = null;
        try {
            body = extractErrorBody(ex);
            return body;
        } finally {
            completeHandlingOfException(ex, body);
        }
    }

    private ServiceErrorModel extractErrorBody(Exception ex) {
        if (ex instanceof ApiException apiException) {
            return apiException.getServiceError();
        }

        ServiceErrorModel serviceError = new ServiceErrorModel();
        serviceError.setMessage(ex.getMessage());

        return serviceError;
    }

    private void completeHandlingOfException(Exception exception, ServiceErrorModel model) {
        log.info(exception.getMessage());
        log.debug("Complete handling of exception " + exception.getClass().getSimpleName(), exception);
        logResponseBody(model);
    }

    private static void logResponseBody(ServiceErrorModel body) {
        log.info("Returned body: {}", body);
    }
}
