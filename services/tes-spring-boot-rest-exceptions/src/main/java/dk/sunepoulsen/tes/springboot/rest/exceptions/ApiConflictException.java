package dk.sunepoulsen.tes.springboot.rest.exceptions;

public class ApiConflictException extends ApiException {
    public ApiConflictException( String message ) {
        super( message );
    }

    public ApiConflictException( String message, Throwable ex ) {
        super( message, ex );
    }

    public ApiConflictException( String param, String message ) {
        super( param, message );
    }

    public ApiConflictException( String param, String message, Throwable ex ) {
        super( param, message, ex );
    }

    public ApiConflictException( String code, String param, String message ) {
        super( code, param, message );
    }

    public ApiConflictException( String code, String param, String message, Throwable ex ) {
        super( code, param, message, ex );
    }

    @Override
    public String toString() {
        return "ApiConflictException{} " + super.toString();
    }
}
