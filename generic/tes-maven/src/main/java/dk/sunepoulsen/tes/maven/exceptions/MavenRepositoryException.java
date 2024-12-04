package dk.sunepoulsen.tes.maven.exceptions;

public class MavenRepositoryException extends RuntimeException {
    public MavenRepositoryException(String message) {
        super(message);
    }

    public MavenRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
