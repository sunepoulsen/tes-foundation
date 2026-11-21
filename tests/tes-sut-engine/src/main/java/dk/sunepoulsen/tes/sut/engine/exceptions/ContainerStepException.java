package dk.sunepoulsen.tes.sut.engine.exceptions;

/** Indicates a container step could not be configured or executed. */
public class ContainerStepException extends RuntimeException {

    /**
     * Creates a container step failure.
     * @param message failure description
     * @param cause underlying cause
     */
    public ContainerStepException(String message, Throwable cause) {
        super(message, cause);
    }
}
