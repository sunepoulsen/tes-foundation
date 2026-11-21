package dk.sunepoulsen.tes.security.exceptions;

/** Signals a failure to create a self-signed certificate. */
public class SelfSignedCertificateFactoryException extends Exception {
    /** @param message description of the failure
     * @param cause underlying cause */
    public SelfSignedCertificateFactoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
