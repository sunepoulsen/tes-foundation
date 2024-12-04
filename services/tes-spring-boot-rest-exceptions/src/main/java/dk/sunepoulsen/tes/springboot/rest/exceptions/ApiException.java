package dk.sunepoulsen.tes.springboot.rest.exceptions;

import dk.sunepoulsen.tes.rest.models.ServiceErrorModel;

import java.util.Objects;

public class ApiException extends RuntimeException {
    private ServiceErrorModel serviceError;

    public ApiException( String message ) {
        this( null, null, message, null );
    }

    public ApiException( String message, Throwable ex ) {
        this( null, null, message, ex );
    }

    public ApiException( String param, String message ) {
        this( null, param, message, null );
    }

    public ApiException( String param, String message, Throwable ex ) {
        this( null, param, message, ex );
    }

    public ApiException( String code, String param, String message ) {
        this( code, param, message, null );
    }

    public ApiException( String code, String param, String message, Throwable ex ) {
        super( message, ex );

        serviceError = new ServiceErrorModel();
        serviceError.setCode( code );
        serviceError.setParam( param );
        serviceError.setMessage( message );
    }

    public ServiceErrorModel getServiceError() {
        return serviceError;
    }

    public void setServiceError( ServiceErrorModel serviceError ) {
        this.serviceError = serviceError;
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
