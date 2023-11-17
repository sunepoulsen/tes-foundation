package dk.sunepoulsen.tes.springboot.rest.exceptions;

import dk.sunepoulsen.tes.rest.models.ServiceErrorModel;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@Slf4j
@ControllerAdvice
public class ServiceExceptionHandler {

    public ServiceExceptionHandler() {
    }

    @Hidden
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler( ApiBadRequestException.class )
    @ResponseBody
    public ServiceErrorModel handleBadRequest(ApiBadRequestException ex ) {
        return handleCheckedException( ex );
    }

    @Hidden
    @ResponseStatus( HttpStatus.UNAUTHORIZED )
    @ExceptionHandler( ApiUnauthorizedException.class )
    @ResponseBody
    public ServiceErrorModel handleUnauthorized(ApiUnauthorizedException ex ) {
        return handleCheckedException( ex );
    }

    @Hidden
    @ResponseStatus( HttpStatus.FORBIDDEN )
    @ExceptionHandler( ApiForbiddenException.class )
    @ResponseBody
    public ServiceErrorModel handleForbidden(ApiForbiddenException ex ) {
        return handleCheckedException( ex );
    }

    @Hidden
    @ResponseStatus( HttpStatus.NOT_FOUND )
    @ExceptionHandler( ApiNotFoundException.class )
    @ResponseBody
    public ServiceErrorModel handleNotFound(ApiNotFoundException ex ) {
        return handleCheckedException( ex );
    }

    @Hidden
    @ResponseStatus( HttpStatus.CONFLICT )
    @ExceptionHandler( ApiConflictException.class )
    @ResponseBody
    public ServiceErrorModel handleConflict(ApiConflictException ex ) {
        return handleCheckedException( ex );
    }

    @Hidden
    @ResponseStatus( HttpStatus.NOT_IMPLEMENTED )
    @ExceptionHandler( UnsupportedOperationException.class )
    @ResponseBody
    public ServiceErrorModel handleUnsupportedOperation(UnsupportedOperationException ex ) {
        return handleCheckedException( ex );
    }

    @Hidden
    @ResponseStatus( HttpStatus.BAD_GATEWAY )
    @ExceptionHandler( ApiBadGatewayException.class )
    @ResponseBody
    public ServiceErrorModel handleBadGateway(ApiBadGatewayException ex ) {
        return handleCheckedException( ex );
    }

    @Hidden
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    @ExceptionHandler( ApiInternalServerException.class )
    @ResponseBody
    public ServiceErrorModel handleInternalServerError(ApiInternalServerException ex ) {
        return handleCheckedException( ex );
    }

    @Hidden
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler( ConstraintViolationException.class )
    @ResponseBody
    public ServiceErrorModel handleConstraintViolationException(ConstraintViolationException ex ) {
        ServiceErrorModel model = null;

        try {
            Optional<ConstraintViolation<?>> optionalViolation = ex.getConstraintViolations().stream().findFirst();
            if (optionalViolation.isEmpty()) {
                return null;
            }

            StringBuilder propertyPathBuilder = new StringBuilder();
            for (Path.Node node : optionalViolation.get().getPropertyPath()) {
                if (node.getKind() == ElementKind.PROPERTY) {
                    if (!propertyPathBuilder.isEmpty()) {
                        propertyPathBuilder.append(".");
                    }
                    propertyPathBuilder.append(node.getName());
                }
            }

            model = new ServiceErrorModel();
            model.setParam(propertyPathBuilder.toString());
            model.setMessage(optionalViolation.get().getMessage());

            return model;
        }
        finally {
            log.info( ex.getMessage() );
            log.debug( "Exception", ex );
            logResponseBody( model );
        }
    }

    @Hidden
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler( MethodArgumentNotValidException.class )
    @ResponseBody
    public ServiceErrorModel handleMethodArgumentNotValidException(MethodArgumentNotValidException ex ) {
        ServiceErrorModel model = null;

        try {
            if (ex.getBindingResult().getFieldError() != null ) {
                model = new ServiceErrorModel();
                model.setParam(ex.getBindingResult().getFieldError().getField());
                model.setMessage(ex.getBindingResult().getFieldError().getDefaultMessage());
            }

            return model;
        }
        finally {
            log.info( ex.getMessage() );
            log.debug( "Exception", ex );
            logResponseBody( model );
        }
    }

    @Hidden
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    @ExceptionHandler( RuntimeException.class )
    @ResponseBody
    public ServiceErrorModel handleRuntimeException(RuntimeException ex ) {
        ServiceErrorModel body = null;
        try {
            body = new ServiceErrorModel();
            body.setMessage( "Unable to process request" );

            return body;
        }
        catch( Exception e ) {
            log.info( e.getMessage(), e );
            return null;
        }
        finally {
            log.info( ex.getMessage(), ex );
            logResponseBody( body );
        }
    }

    private ServiceErrorModel handleCheckedException( Exception ex ) {
        ServiceErrorModel body = null;
        try {
            return body = extractErrorBody( ex );
        }
        catch( Exception e ) {
            log.info( e.getMessage(), e );
            return null;
        }
        finally {
            log.info( ex.getMessage() );
            log.debug( "Exception", ex );
            logResponseBody( body );
        }
    }

    private ServiceErrorModel extractErrorBody( Exception ex ) {
        if( ex instanceof ApiException) {
            return ((ApiException)ex).getServiceError();
        }

        ServiceErrorModel serviceError = new ServiceErrorModel();
        serviceError.setMessage(ex.getMessage());

        return serviceError;
    }

    private static void logResponseBody( ServiceErrorModel body ) {
        log.info( "Returned body: {}", body );
    }
}
