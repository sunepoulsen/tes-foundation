package dk.sunepoulsen.tes.springboot.rest.exceptions;

/**
 * Created by sunepoulsen on 26/11/2016.
 */
public class ApiBadRequestException extends ApiException {
    /**
     * Creates a bad request error with a message.
     * @param message the error message
     */
    public ApiBadRequestException( String message ) {
        super( message );
    }

    /**
     * Creates a bad request error with a message and cause.
     * @param message the error message
     * @param ex the cause
     */
    public ApiBadRequestException( String message, Throwable ex ) {
        super( message, ex );
    }

    /**
     * Creates a bad request error for a parameter.
     * @param param the associated parameter
     * @param message the error message
     */
    public ApiBadRequestException( String param, String message ) {
        super( param, message );
    }

    /**
     * Creates a bad request error for a parameter with a cause.
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public ApiBadRequestException( String param, String message, Throwable ex ) {
        super( param, message, ex );
    }

    /**
     * Creates a bad request error with a code and parameter.
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     */
    public ApiBadRequestException( String code, String param, String message ) {
        super( code, param, message );
    }

    /**
     * Creates a bad request error with a code, parameter and cause.
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public ApiBadRequestException( String code, String param, String message, Throwable ex ) {
        super( code, param, message, ex );
    }

    @Override
    public String toString() {
        return "ApiBadRequestException{} " + super.toString();
    }
}
