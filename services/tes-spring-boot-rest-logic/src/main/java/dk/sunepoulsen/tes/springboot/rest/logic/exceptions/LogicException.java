package dk.sunepoulsen.tes.springboot.rest.logic.exceptions;

import dk.sunepoulsen.tes.springboot.rest.exceptions.ApiException;

import java.util.Objects;

public abstract class LogicException extends RuntimeException {
    private String code;
    private String param;

    public LogicException( String message ) {
        this( null, null, message, null );
    }

    public LogicException( String message, Throwable ex ) {
        this( null, null, message, ex );
    }

    public LogicException( String param, String message ) {
        this( null, param, message, null );
    }

    public LogicException( String param, String message, Throwable ex ) {
        this( null, param, message, ex );
    }

    public LogicException( String code, String param, String message ) {
        this( code, param, message, null );
    }

    public LogicException( String code, String param, String message, Throwable ex ) {
        super( message, ex );

        this.code = code;
        this.param = param;
    }

    public String getCode() {
        return code;
    }

    public String getParam() {
        return param;
    }

    public abstract ApiException mapApiException();

    public void throwApiException() throws ApiException {
        throw mapApiException();
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) {
            return true;
        }
        if( !( o instanceof LogicException) ) {
            return false;
        }
        LogicException that = (LogicException) o;
        return Objects.equals( code, that.code ) &&
            Objects.equals( param, that.param ) &&
            Objects.equals( getMessage(), that.getMessage() );
    }

    @Override
    public int hashCode() {
        return Objects.hash( code, param, getMessage() );
    }

    @Override
    public String toString() {
        return "LogicException{" +
            "code='" + code + '\'' +
            ", param='" + param + '\'' +
            ", message='" + getMessage() + '\'' +
            '}';
    }
}
