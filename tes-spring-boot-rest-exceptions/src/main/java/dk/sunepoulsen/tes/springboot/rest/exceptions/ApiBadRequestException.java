package dk.sunepoulsen.tes.springboot.rest.exceptions;

/**
 * Created by sunepoulsen on 26/11/2016.
 */
public class ApiBadRequestException extends ApiException {
    public ApiBadRequestException( String message ) {
        super( message );
    }

    public ApiBadRequestException( String message, Throwable ex ) {
        super( message, ex );
    }

    public ApiBadRequestException( String param, String message ) {
        super( param, message );
    }

    public ApiBadRequestException( String param, String message, Throwable ex ) {
        super( param, message, ex );
    }

    public ApiBadRequestException( String code, String param, String message ) {
        super( code, param, message );
    }

    public ApiBadRequestException( String code, String param, String message, Throwable ex ) {
        super( code, param, message, ex );
    }

    @Override
    public String toString() {
        return "ApiBadRequestException{} " + super.toString();
    }
}
