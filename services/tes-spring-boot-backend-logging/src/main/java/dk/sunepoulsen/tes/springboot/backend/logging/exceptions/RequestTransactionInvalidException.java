package dk.sunepoulsen.tes.springboot.backend.logging.exceptions;

/** Indicates an invalid request transaction. */
public class RequestTransactionInvalidException extends RuntimeException {
    /**
     * Creates an exception describing the invalid transaction.
     *
     * @param message the detail message
     */
    public RequestTransactionInvalidException(String message) {
        super(message);
    }
}
