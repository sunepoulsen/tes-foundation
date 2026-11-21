package dk.sunepoulsen.tes.springboot.backend.logging.exceptions;

import lombok.Getter;

/** Indicates an invalid request header value. */
@Getter
public class RequestHeaderValueException extends RuntimeException {

    /** Name of the header. */
    private final String headerName;
    /** Value supplied for the header. */
    private final String headerValue;

    /**
     * Creates an exception describing a request header value.
     *
     * @param headerName the header name
     * @param headerValue the header value
     * @param message the detail message
     * @param cause the underlying cause
     */
    public RequestHeaderValueException(final String headerName, final String headerValue, final String message, final Throwable cause) {
        super(message, cause);
        this.headerName = headerName;
        this.headerValue = headerValue;
    }

}
