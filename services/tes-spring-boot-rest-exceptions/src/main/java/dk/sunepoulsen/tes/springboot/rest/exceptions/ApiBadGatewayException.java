package dk.sunepoulsen.tes.springboot.rest.exceptions;

/**
 * Created by sunepoulsen on 26/11/2016.
 */
public class ApiBadGatewayException extends ApiException {
    /**
     * Creates a bad gateway API exception.
     *
     * @param message the error message
     */
    public ApiBadGatewayException( String message ) {
        super( message );
    }

    /**
     * Creates a bad gateway API exception.
     *
     * @param message the error message
     * @param ex the cause
     */
    public ApiBadGatewayException( String message, Throwable ex ) {
        super( message, ex );
    }

    /**
     * Creates a bad gateway API exception.
     *
     * @param param the associated parameter
     * @param message the error message
     */
    public ApiBadGatewayException( String param, String message ) {
        super( param, message );
    }

    /**
     * Creates a bad gateway API exception.
     *
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public ApiBadGatewayException( String param, String message, Throwable ex ) {
        super( param, message, ex );
    }

    /**
     * Creates a bad gateway API exception.
     *
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     */
    public ApiBadGatewayException( String code, String param, String message ) {
        super( code, param, message );
    }

    /**
     * Creates a bad gateway API exception.
     *
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public ApiBadGatewayException( String code, String param, String message, Throwable ex ) {
        super( code, param, message, ex );
    }

    @Override
    public String toString() {
        return "ApiBadGatewayException{} " + super.toString();
    }
}
