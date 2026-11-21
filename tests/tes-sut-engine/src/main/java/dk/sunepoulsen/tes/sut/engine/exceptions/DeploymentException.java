package dk.sunepoulsen.tes.sut.engine.exceptions;

/** Indicates a system-under-test deployment failure. */
public class DeploymentException extends RuntimeException {

    /**
     * Creates a deployment failure.
     * @param message failure description
     * @param cause underlying cause
     */
    public DeploymentException(String message, Throwable cause) {
        super(message, cause);
    }
}
