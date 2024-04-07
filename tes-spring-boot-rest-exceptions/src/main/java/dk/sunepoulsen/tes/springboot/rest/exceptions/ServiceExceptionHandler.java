package dk.sunepoulsen.tes.springboot.rest.exceptions;

import dk.sunepoulsen.tes.rest.models.ServiceErrorModel;
import dk.sunepoulsen.tes.rest.models.ServiceValidationError;
import dk.sunepoulsen.tes.rest.models.ServiceValidationErrorModel;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ServiceExceptionHandler {

    public ServiceExceptionHandler() {
    }

    @Hidden
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiBadRequestException.class)
    @ResponseBody
    public ServiceErrorModel handleBadRequest(ApiBadRequestException ex) {
        return handleCheckedException(ex);
    }

    @Hidden
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ApiUnauthorizedException.class)
    @ResponseBody
    public ServiceErrorModel handleUnauthorized(ApiUnauthorizedException ex) {
        return handleCheckedException(ex);
    }

    @Hidden
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ApiForbiddenException.class)
    @ResponseBody
    public ServiceErrorModel handleForbidden(ApiForbiddenException ex) {
        return handleCheckedException(ex);
    }

    @Hidden
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ApiNotFoundException.class)
    @ResponseBody
    public ServiceErrorModel handleNotFound(ApiNotFoundException ex) {
        return handleCheckedException(ex);
    }

    @Hidden
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ApiConflictException.class)
    @ResponseBody
    public ServiceErrorModel handleConflict(ApiConflictException ex) {
        return handleCheckedException(ex);
    }

    @Hidden
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseBody
    public ServiceErrorModel handleUnsupportedOperation(UnsupportedOperationException ex) {
        return handleCheckedException(ex);
    }

    @Hidden
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(ApiBadGatewayException.class)
    @ResponseBody
    public ServiceErrorModel handleBadGateway(ApiBadGatewayException ex) {
        return handleCheckedException(ex);
    }

    @Hidden
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApiInternalServerException.class)
    @ResponseBody
    public ServiceErrorModel handleInternalServerError(ApiInternalServerException ex) {
        return handleCheckedException(ex);
    }

    @Hidden
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ServiceValidationErrorModel handleConstraintViolationException(ConstraintViolationException ex) {
        ServiceValidationErrorModel model = new ServiceValidationErrorModel();

        try {
            model.setMessage("Unable to process request because of validation errors");
            model.setValidationErrors(ValidationErrorsTransformations.toServiceValidationErrors(ex));
            return model;
        } finally {
            log.info(ex.getMessage());
            log.debug("Complete handling of exception " + ex.getClass().getSimpleName(), ex);
            logResponseBody(model);
        }
    }

    @Hidden
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ServiceValidationErrorModel handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ServiceValidationErrorModel model = new ServiceValidationErrorModel();

        try {
            model.setMessage("Unable to process request because of validation errors");
            model.setValidationErrors(ValidationErrorsTransformations.toServiceValidationErrors(ex.getFieldErrors()));
            return model;
        } finally {
            log.info(ex.getMessage());
            log.debug("Complete handling of exception " + ex.getClass().getSimpleName(), ex);
            logResponseBody(model);
        }
    }

    @Hidden
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ServiceErrorModel handleRuntimeException(RuntimeException ex) {
        ServiceErrorModel body = null;
        try {
            body = new ServiceErrorModel();
            body.setMessage("Unable to process request");

            return body;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return null;
        } finally {
            log.info(ex.getMessage(), ex);
            logResponseBody(body);
        }
    }

    private ServiceErrorModel handleCheckedException(Exception ex) {
        ServiceErrorModel body = null;
        try {
            return body = extractErrorBody(ex);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return null;
        } finally {
            log.info(ex.getMessage());
            log.debug("Complete handling of exception " + ex.getClass().getSimpleName(), ex);
            logResponseBody(body);
        }
    }

    private ServiceErrorModel extractErrorBody(Exception ex) {
        if (ex instanceof ApiException) {
            return ((ApiException) ex).getServiceError();
        }

        ServiceErrorModel serviceError = new ServiceErrorModel();
        serviceError.setMessage(ex.getMessage());

        return serviceError;
    }

    private static void logResponseBody(ServiceErrorModel body) {
        log.info("Returned body: {}", body);
    }
}
