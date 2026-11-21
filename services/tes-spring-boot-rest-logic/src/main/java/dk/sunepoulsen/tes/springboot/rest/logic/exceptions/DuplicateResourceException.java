package dk.sunepoulsen.tes.springboot.rest.logic.exceptions;


import dk.sunepoulsen.tes.springboot.rest.exceptions.ApiConflictException;
import dk.sunepoulsen.tes.springboot.rest.exceptions.ApiException;

/**
 * Exception to report violations of unique resources in the database.
 */
public class DuplicateResourceException extends LogicException {
    /**
     * Creates an exception with a message.
     * @param message the error message
     */
    public DuplicateResourceException( String message ) {
        super( message );
    }

    /**
     * Creates an exception with a message and cause.
     * @param message the error message
     * @param ex the cause
     */
    public DuplicateResourceException( String message, Throwable ex ) {
        super( message, ex );
    }

    /**
     * Creates an exception for a parameter.
     * @param param the associated parameter
     * @param message the error message
     */
    public DuplicateResourceException( String param, String message ) {
        super( param, message );
    }

    /**
     * Creates an exception for a parameter with a cause.
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public DuplicateResourceException( String param, String message, Throwable ex ) {
        super( param, message, ex );
    }

    /**
     * Creates an exception with a code and parameter.
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     */
    public DuplicateResourceException( String code, String param, String message ) {
        super( code, param, message );
    }

    /**
     * Creates an exception with a code, parameter and cause.
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public DuplicateResourceException( String code, String param, String message, Throwable ex ) {
        super( code, param, message, ex );
    }

    @Override
    public ApiException mapApiException() {
        return new ApiConflictException( getCode(), getParam(), getMessage(), this );
    }

    @Override
    public String toString() {
        return "DuplicateResourceException{} " + super.toString();
    }
}
