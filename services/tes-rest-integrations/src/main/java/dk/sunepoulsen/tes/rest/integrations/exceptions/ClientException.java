package dk.sunepoulsen.tes.rest.integrations.exceptions;

import dk.sunepoulsen.tes.rest.models.ServiceErrorModel;

import java.util.Objects;

/** An integration failure described by a service error. */
public class ClientException extends RuntimeException {
    /** The service error associated with this failure. */
    private final ServiceErrorModel serviceError;

    /**
     * Creates an exception for a service error.
     *
     * @param serviceError the error providing the exception message
     */
    public ClientException(ServiceErrorModel serviceError) {
        this(serviceError, null);
    }

    /**
     * Creates an exception for a service error and its cause.
     *
     * @param serviceError the error providing the exception message
     * @param throwable the cause, or null
     */
    public ClientException(ServiceErrorModel serviceError, Throwable throwable) {
        super(serviceError.getMessage(), throwable);
        this.serviceError = serviceError;
    }

    /**
     * Returns the associated service error.
     *
     * @return the service error
     */
    public ServiceErrorModel getServiceError() {
        return serviceError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientException that = (ClientException) o;
        return Objects.equals(serviceError, that.serviceError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceError);
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "serviceError=" + serviceError +
                '}';
    }
}
