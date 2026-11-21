package dk.sunepoulsen.tes.springboot.rest.logic.exceptions;

/**
 * Exception that is intended to report persistence errors
 * in services.
 */
public class PersistenceException extends RuntimeException {
    /**
     * Creates a persistence error with a message.
     * @param message the error message
     */
    public PersistenceException( String message ) {
        super( message );
    }

    /**
     * Creates a persistence error with a message and cause.
     * @param message the error message
     * @param cause the cause
     */
    public PersistenceException( String message, Throwable cause ) {
        super( message, cause );
    }

    /**
     * Creates a persistence error using the cause's message.
     * @param ex the cause
     * @throws NullPointerException if the cause is null
     */
    public PersistenceException( Exception ex ) {
        this( ex.getMessage(), ex );
    }
}
