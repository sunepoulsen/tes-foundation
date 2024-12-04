package dk.sunepoulsen.tes.springboot.rest.exceptions;

/**
 * Api exception to report a forbidden response in rest controllers.
 * <p>
 *     This exception is handled by the default exception handler.
 * </p>
 */
public class ApiForbiddenException extends ApiException {
    public ApiForbiddenException( String message ) {
        super( message );
    }

    public ApiForbiddenException( String message, Throwable ex ) {
        super( message, ex );
    }

    public ApiForbiddenException( String param, String message ) {
        super( param, message );
    }

    public ApiForbiddenException( String param, String message, Throwable ex ) {
        super( param, message, ex );
    }

    public ApiForbiddenException( String code, String param, String message ) {
        super( code, param, message );
    }

    public ApiForbiddenException( String code, String param, String message, Throwable ex ) {
        super( code, param, message, ex );
    }

    public String toString() {
        return "ApiForbiddenException{} " + super.toString();
    }
}
