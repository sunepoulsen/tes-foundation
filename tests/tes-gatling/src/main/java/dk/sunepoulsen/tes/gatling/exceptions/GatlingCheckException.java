package dk.sunepoulsen.tes.gatling.exceptions;

/** Indicates that a Gatling check failed. */
public class GatlingCheckException extends RuntimeException {

    /**
     * Creates a check failure.
     * @param message description of the failure
     */
    public GatlingCheckException(String message) {
        super(message);
    }

}
