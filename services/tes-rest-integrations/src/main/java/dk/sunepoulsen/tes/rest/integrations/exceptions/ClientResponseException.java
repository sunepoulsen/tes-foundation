package dk.sunepoulsen.tes.rest.integrations.exceptions;

import dk.sunepoulsen.tes.rest.models.ServiceErrorModel;
import lombok.Getter;

import java.net.http.HttpResponse;
import java.util.Objects;

/** An integration failure with its associated HTTP response. */
@Getter
public class ClientResponseException extends ClientException {
    /** The response associated with the failure. */
    private final transient HttpResponse<String> response;

    /**
     * Creates an exception for a response and service error.
     *
     * @param response the HTTP response
     * @param serviceError the service error
     */
    public ClientResponseException(HttpResponse<String> response, ServiceErrorModel serviceError) {
        this(response, serviceError, null);
    }

    /**
     * Creates an exception for a response, service error and cause.
     *
     * @param response the HTTP response
     * @param serviceError the service error
     * @param throwable the cause, or null
     */
    public ClientResponseException(HttpResponse<String> response, ServiceErrorModel serviceError, Throwable throwable) {
        super(serviceError, throwable);
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClientResponseException that = (ClientResponseException) o;
        return Objects.equals(response, that.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), response);
    }

    @Override
    public String toString() {
        return "ClientResponseException{" +
                "response=" + response +
                "} " + super.toString();
    }
}
