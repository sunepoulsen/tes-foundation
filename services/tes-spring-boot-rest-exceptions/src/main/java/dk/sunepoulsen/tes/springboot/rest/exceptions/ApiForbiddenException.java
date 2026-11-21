package dk.sunepoulsen.tes.springboot.rest.exceptions;

/**
 * Api exception to report a forbidden response in rest controllers.
 * <p>
 *     This exception is handled by the default exception handler.
 * </p>
 */
public class ApiForbiddenException extends ApiException {
    /**
     * Creates a forbidden API exception.
     *
     * @param message the error message
     */
    public ApiForbiddenException( String message ) {
        super( message );
    }

    /**
     * Creates a forbidden API exception.
     *
     * @param message the error message
     * @param ex the cause
     */
    public ApiForbiddenException( String message, Throwable ex ) {
        super( message, ex );
    }

    /**
     * Creates a forbidden API exception.
     *
     * @param param the associated parameter
     * @param message the error message
     */
    public ApiForbiddenException( String param, String message ) {
        super( param, message );
    }

    /**
     * Creates a forbidden API exception.
     *
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public ApiForbiddenException( String param, String message, Throwable ex ) {
        super( param, message, ex );
    }

    /**
     * Creates a forbidden API exception.
     *
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     */
    public ApiForbiddenException( String code, String param, String message ) {
        super( code, param, message );
    }

    /**
     * Creates a forbidden API exception.
     *
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public ApiForbiddenException( String code, String param, String message, Throwable ex ) {
        super( code, param, message, ex );
    }

    @Override
    public String toString() {
        return "ApiForbiddenException{} " + super.toString();
    }
}
