package dk.sunepoulsen.tes.flows.exceptions;

public class FlowStepException extends RuntimeException {
    public FlowStepException(String message) {
        super(message);
    }

    public FlowStepException(String message, Throwable cause) {
        super(message, cause);
    }
}
