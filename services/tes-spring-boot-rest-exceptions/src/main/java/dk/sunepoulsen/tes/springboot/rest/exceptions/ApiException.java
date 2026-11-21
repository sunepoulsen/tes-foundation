package dk.sunepoulsen.tes.springboot.rest.exceptions;

import dk.sunepoulsen.tes.rest.models.ServiceErrorModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/** An API error carrying a service error model. */
@Getter
@Setter
public class ApiException extends RuntimeException {
    /** Error details exposed by this exception. */
    private final ServiceErrorModel serviceError;

    /**
     * Creates an API error with a message.
     * @param message the error message
     */
    public ApiException( String message ) {
        this( null, null, message, null );
    }

    /**
     * Creates an API error with a message and cause.
     * @param message the error message
     * @param ex the cause
     */
    public ApiException( String message, Throwable ex ) {
        this( null, null, message, ex );
    }

    /**
     * Creates an API error for a parameter.
     * @param param the associated parameter
     * @param message the error message
     */
    public ApiException( String param, String message ) {
        this( null, param, message, null );
    }

    /**
     * Creates an API error for a parameter with a cause.
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public ApiException( String param, String message, Throwable ex ) {
        this( null, param, message, ex );
    }

    /**
     * Creates an API error with a code and parameter.
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     */
    public ApiException( String code, String param, String message ) {
        this( code, param, message, null );
    }

    /**
     * Creates an API error with a code, parameter and cause.
     * @param code the error code
     * @param param the associated parameter
     * @param message the error message
     * @param ex the cause
     */
    public ApiException( String code, String param, String message, Throwable ex ) {
        super( message, ex );

        serviceError = new ServiceErrorModel();
        serviceError.setCode( code );
        serviceError.setParam( param );
        serviceError.setMessage( message );
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) {
            return true;
        }
        if( !( o instanceof ApiException) ) {
            return false;
        }
        ApiException that = (ApiException) o;
        return Objects.equals( serviceError, that.serviceError );
    }

    @Override
    public int hashCode() {
        return Objects.hash( serviceError );
    }

    @Override
    public String toString() {
        return "ApiException{" +
            "serviceError=" + serviceError +
            '}';
    }
}
