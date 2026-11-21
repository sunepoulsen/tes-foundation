package dk.sunepoulsen.tes.springboot.rest.logic.exceptions;

import dk.sunepoulsen.tes.springboot.rest.exceptions.ApiException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/** Base class for logic failures that can be mapped to API exceptions. */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public abstract class LogicException extends RuntimeException {
    /** Optional error code. */
    private final String code;
    /** Optional parameter associated with the error. */
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

    /**
     * Maps this failure to an API exception.
     *
     * @return the corresponding API exception
     */
    public abstract ApiException mapApiException();

    /**
     * Throws the API exception produced by {@link #mapApiException()}.
     *
     * @throws ApiException the mapped API exception
     */
    public void throwApiException() throws ApiException {
        throw mapApiException();
    }

}
