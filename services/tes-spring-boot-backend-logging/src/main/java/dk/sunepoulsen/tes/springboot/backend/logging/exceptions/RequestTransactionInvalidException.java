package dk.sunepoulsen.tes.springboot.backend.logging.exceptions;

public class RequestTransactionInvalidException extends RuntimeException {
    public RequestTransactionInvalidException(String message) {
        super(message);
    }
}
