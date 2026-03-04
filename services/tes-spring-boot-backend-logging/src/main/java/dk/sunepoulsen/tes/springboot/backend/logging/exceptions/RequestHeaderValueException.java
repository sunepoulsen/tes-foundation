package dk.sunepoulsen.tes.springboot.backend.logging.exceptions;

import lombok.Getter;

@Getter
public class RequestHeaderValueException extends RuntimeException {

    private final String headerName;
    private final String headerValue;

    public RequestHeaderValueException(final String headerName, final String headerValue, final String message, final Throwable cause) {
        super(message, cause);
        this.headerName = headerName;
        this.headerValue = headerValue;
    }

}
