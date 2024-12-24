package dk.sunepoulsen.tes.security.exceptions;

public class SelfSignedCertificateFactoryException extends Exception {
    public SelfSignedCertificateFactoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
