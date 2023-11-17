package dk.sunepoulsen.tes.springboot.rest.exceptions;

/**
 * Created by sunepoulsen on 19/12/2016.
 */
public class ApiUnauthorizedException extends ApiException {
    public ApiUnauthorizedException( Throwable ex ) {
        super( "Unable to authenticate user", ex );
    }

    @Override
    public String toString() {
        return "ApiUnauthorizedException{} " + super.toString();
    }
}
