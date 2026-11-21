package dk.sunepoulsen.tes.maven.exceptions;

/** Signals a Maven repository operation failure. */
public class MavenRepositoryException extends RuntimeException {
    /** @param message description of the failure */
    public MavenRepositoryException(String message) {
        super(message);
    }

    /** @param message description of the failure
     * @param cause underlying cause */
    public MavenRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
