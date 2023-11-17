package dk.sunepoulsen.tes.springboot.rest.exceptions;

/**
 * Created by sunepoulsen on 26/11/2016.
 */
public class ApiBadGatewayException extends ApiException {
    public ApiBadGatewayException( String message ) {
        super( message );
    }

    public ApiBadGatewayException( String message, Throwable ex ) {
        super( message, ex );
    }

    public ApiBadGatewayException( String param, String message ) {
        super( param, message );
    }

    public ApiBadGatewayException( String param, String message, Throwable ex ) {
        super( param, message, ex );
    }

    public ApiBadGatewayException( String code, String param, String message ) {
        super( code, param, message );
    }

    public ApiBadGatewayException( String code, String param, String message, Throwable ex ) {
        super( code, param, message, ex );
    }

    @Override
    public String toString() {
        return "ApiBadGatewayException{} " + super.toString();
    }
}
