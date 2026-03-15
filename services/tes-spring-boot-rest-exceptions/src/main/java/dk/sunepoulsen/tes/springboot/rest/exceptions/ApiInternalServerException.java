package dk.sunepoulsen.tes.springboot.rest.exceptions;

/**
 * Created by sunepoulsen on 19/12/2016.
 */
public class ApiInternalServerException extends ApiException {

    public static final String EXCEPTION_MESSAGE = "Unable to complete request";

    public ApiInternalServerException() {
        super( EXCEPTION_MESSAGE );
    }

    public ApiInternalServerException( Throwable ex ) {
        super( EXCEPTION_MESSAGE, ex );
    }

    @Override
    public String toString() {
        return "ApiInternalServerException{} " + super.toString();
    }
}
