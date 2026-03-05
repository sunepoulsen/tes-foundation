package dk.sunepoulsen.tes.springboot.rest.logic.exceptions;

import dk.sunepoulsen.tes.springboot.rest.exceptions.ApiException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public abstract class LogicException extends RuntimeException {
    private final String code;
    private final String param;

    protected LogicException( String message ) {
        this( null, null, message, null );
    }

    protected LogicException( String message, Throwable ex ) {
        this( null, null, message, ex );
    }

    protected LogicException( String param, String message ) {
        this( null, param, message, null );
    }

    protected LogicException( String param, String message, Throwable ex ) {
        this( null, param, message, ex );
    }

    protected LogicException( String code, String param, String message ) {
        this( code, param, message, null );
    }

    protected LogicException( String code, String param, String message, Throwable ex ) {
        super( message, ex );

        this.code = code;
        this.param = param;
    }

    public abstract ApiException mapApiException();

    public void throwApiException() throws ApiException {
        throw mapApiException();
    }

}
