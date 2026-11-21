package dk.sunepoulsen.tes.flows.exceptions;

/** Signals a failure in a flow step. */
public class FlowStepException extends RuntimeException {
    /** @param message description of the failure */
    public FlowStepException(String message) {
        super(message);
    }

    /** @param message description of the failure
     * @param cause underlying cause */
    public FlowStepException(String message, Throwable cause) {
        super(message, cause);
    }
}
