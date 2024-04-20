package dk.sunepoulsen.tes.utils.exceptions;

/**
 * Exception to report errors from {@code PropertyResource}
 */
public class PropertyResourceException extends Exception {

    /**
     * Constructs a new {@code PropertyResourceException}
     *
     * @param message Error message.
     */
    public PropertyResourceException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code PropertyResourceException}
     *
     * @param message Error message.
     * @param cause Caused exception
     */
    public PropertyResourceException(String message, Throwable cause) {
        super(message, cause);
    }

}
