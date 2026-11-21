package dk.sunepoulsen.tes.io.resources;

/** Signals a resource loading failure. */
public class ResourceException extends Exception{

    /** @param message description of the failure */
    public ResourceException(String message) {
        super(message);
    }

    /** @param message description of the failure
     * @param cause underlying cause */
    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }

}
