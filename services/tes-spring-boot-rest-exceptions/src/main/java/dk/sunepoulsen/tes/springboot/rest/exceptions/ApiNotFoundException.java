package dk.sunepoulsen.tes.springboot.rest.exceptions;

/**
 * API exception representing a not found response.
 */
public class ApiNotFoundException extends ApiException {
    /**
     * Creates a not found API exception.
     *
     * @param message the error message
     */
    public ApiNotFoundException( String message ) {
        super( message );
    }

    /**
     * Creates a not found API exception.
     *
     * @param message the error message
     * @param ex the cause
     */
    public ApiNotFoundException( String message, Throwable ex ) {
        super( message, ex );
    }

    /**
     * Creates a not found API exception.
     *
     * @param param the associated parameter
     * @param message the error message
     */
    public ApiNotFoundException( String param, String message ) {
        super( param, message );
    }

    /**
     * Creates a not found API exception.
     *
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public ApiNotFoundException( String param, String message, Throwable ex ) {
        super( param, message, ex );
    }

    /**
     * Creates a not found API exception.
     *
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     */
    public ApiNotFoundException( String code, String param, String message ) {
        super( code, param, message );
    }

    /**
     * Creates a not found API exception.
     *
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public ApiNotFoundException( String code, String param, String message, Throwable ex ) {
        super( code, param, message, ex );
    }

    @Override
    public String toString() {
        return "ApiNotFoundException{} " + super.toString();
    }
}
