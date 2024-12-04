package dk.sunepoulsen.tes.springboot.rest.exceptions;

public class ApiNotFoundException extends ApiException {
    public ApiNotFoundException( String message ) {
        super( message );
    }

    public ApiNotFoundException( String message, Throwable ex ) {
        super( message, ex );
    }

    public ApiNotFoundException( String param, String message ) {
        super( param, message );
    }

    public ApiNotFoundException( String param, String message, Throwable ex ) {
        super( param, message, ex );
    }

    public ApiNotFoundException( String code, String param, String message ) {
        super( code, param, message );
    }

    public ApiNotFoundException( String code, String param, String message, Throwable ex ) {
        super( code, param, message, ex );
    }

    @Override
    public String toString() {
        return "ApiNotFoundException{} " + super.toString();
    }
}
