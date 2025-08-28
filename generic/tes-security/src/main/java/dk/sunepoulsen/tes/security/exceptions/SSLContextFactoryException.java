package dk.sunepoulsen.tes.security.exceptions;

public class SSLContextFactoryException extends Exception {
    public SSLContextFactoryException(String message) {
        super(message);
    }

    public SSLContextFactoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
