package dk.sunepoulsen.tes.springboot.rest.exceptions;

/**
 * API exception representing a conflict response.
 */
public class ApiConflictException extends ApiException {
    /**
     * Creates a conflict API exception.
     *
     * @param message the error message
     */
    public ApiConflictException( String message ) {
        super( message );
    }

    /**
     * Creates a conflict API exception.
     *
     * @param message the error message
     * @param ex the cause
     */
    public ApiConflictException( String message, Throwable ex ) {
        super( message, ex );
    }

    /**
     * Creates a conflict API exception.
     *
     * @param param the associated parameter
     * @param message the error message
     */
    public ApiConflictException( String param, String message ) {
        super( param, message );
    }

    /**
     * Creates a conflict API exception.
     *
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public ApiConflictException( String param, String message, Throwable ex ) {
        super( param, message, ex );
    }

    /**
     * Creates a conflict API exception.
     *
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     */
    public ApiConflictException( String code, String param, String message ) {
        super( code, param, message );
    }

    /**
     * Creates a conflict API exception.
     *
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public ApiConflictException( String code, String param, String message, Throwable ex ) {
        super( code, param, message, ex );
    }

    @Override
    public String toString() {
        return "ApiConflictException{} " + super.toString();
    }
}
