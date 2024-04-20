package dk.sunepoulsen.tes.utils.exceptions;

/**
 * Exception to report errors from {@code Resources}
 */
public class ResourceException extends Exception {

    /**
     * Constructs a new {@code ResourceException}
     *
     * @param message Error message.
     */
    public ResourceException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ResourceException}
     *
     * @param message Error message.
     * @param cause Caused exception
     */
    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }

}
