package dk.sunepoulsen.tes.springboot.rest.logic.exceptions;


import dk.sunepoulsen.tes.springboot.rest.exceptions.ApiConflictException;
import dk.sunepoulsen.tes.springboot.rest.exceptions.ApiException;

/**
 * Exception to report violations of unique resources in the database.
 */
public class DuplicateResourceException extends LogicException {
    public DuplicateResourceException( String message ) {
        super( message );
    }

    public DuplicateResourceException( String message, Throwable ex ) {
        super( message, ex );
    }

    public DuplicateResourceException( String param, String message ) {
        super( param, message );
    }

    public DuplicateResourceException( String param, String message, Throwable ex ) {
        super( param, message, ex );
    }

    public DuplicateResourceException( String code, String param, String message ) {
        super( code, param, message );
    }

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
