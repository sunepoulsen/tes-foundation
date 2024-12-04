package dk.sunepoulsen.tes.docker.exceptions;

public class DockerImageProviderException extends Exception {
    public DockerImageProviderException(String message) {
        super(message);
    }

    public DockerImageProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
