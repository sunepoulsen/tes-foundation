package dk.sunepoulsen.tes.springboot.rest.logic.exceptions;

import dk.sunepoulsen.tes.springboot.rest.exceptions.ApiException;
import dk.sunepoulsen.tes.springboot.rest.exceptions.ApiNotFoundException;

/** Signals that a requested resource was not found. */
public class ResourceNotFoundException extends LogicException {
    public static final String DEFAULT_MESSAGE = "Resource does not exist";

    /**
     * Creates an exception with a message.
     * @param message the error message
     */
    public ResourceNotFoundException( String message ) {
        super( message );
    }

    /**
     * Creates an exception with a message and cause.
     * @param message the error message
     * @param ex the cause
     */
    public ResourceNotFoundException( String message, Throwable ex ) {
        super( message, ex );
    }

    /**
     * Creates an exception for a parameter.
     * @param param the associated parameter
     * @param message the error message
     */
    public ResourceNotFoundException( String param, String message ) {
        super( param, message );
    }

    /**
     * Creates an exception for a parameter with a cause.
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public ResourceNotFoundException( String param, String message, Throwable ex ) {
        super( param, message, ex );
    }

    /**
     * Creates an exception with a code and parameter.
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     */
    public ResourceNotFoundException( String code, String param, String message ) {
        super( code, param, message );
    }

    /**
     * Creates an exception with a code, parameter and cause.
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public ResourceNotFoundException( String code, String param, String message, Throwable ex ) {
        super( code, param, message, ex );
    }

    @Override
    public ApiException mapApiException() {
        return new ApiNotFoundException( getCode(), getParam(), getMessage(), this );
    }

    @Override
    public String toString() {
        return "ResourceNotFoundException{} " + super.toString();
    }
}
