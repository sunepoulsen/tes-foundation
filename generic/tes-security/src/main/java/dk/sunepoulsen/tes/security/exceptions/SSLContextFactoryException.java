package dk.sunepoulsen.tes.security.exceptions;

/** Signals a failure to create an SSL context. */
public class SSLContextFactoryException extends Exception {
    /** @param message description of the failure */
    public SSLContextFactoryException(String message) {
        super(message);
    }

    /** @param message description of the failure
     * @param cause underlying cause */
    public SSLContextFactoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
