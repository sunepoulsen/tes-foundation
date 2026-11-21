package dk.sunepoulsen.tes.rest.integrations.exceptions;

import dk.sunepoulsen.tes.rest.models.ServiceErrorModel;

import java.net.http.HttpResponse;

/** An integration failure associated with a not found response. */
public class ClientNotFoundException extends ClientResponseException {
    /**
     * Creates an exception for a response and service error.
     * @param response the HTTP response
     * @param serviceError the service error
     */
    public ClientNotFoundException(HttpResponse<String> response, ServiceErrorModel serviceError) {
        super(response, serviceError);
    }

    /**
     * Creates an exception for a response, service error and cause.
     * @param response the HTTP response
     * @param serviceError the service error
     * @param throwable the cause, or null
     */
    public ClientNotFoundException(HttpResponse<String> response, ServiceErrorModel serviceError, Throwable throwable) {
        super(response, serviceError, throwable);
    }

    @Override
    public String toString() {
        return "ClientNotFoundException{} " + super.toString();
    }
}
