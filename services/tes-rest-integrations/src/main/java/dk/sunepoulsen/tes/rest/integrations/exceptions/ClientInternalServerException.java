package dk.sunepoulsen.tes.rest.integrations.exceptions;

import dk.sunepoulsen.tes.rest.models.ServiceErrorModel;

import java.net.http.HttpResponse;

/** An integration failure associated with an internal server error response. */
public class ClientInternalServerException extends ClientResponseException {
    /**
     * Creates an exception for a response and service error.
     * @param response the HTTP response
     * @param serviceError the service error
     */
    public ClientInternalServerException(HttpResponse<String> response, ServiceErrorModel serviceError) {
        super(response, serviceError);
    }

    /**
     * Creates an exception for a response, service error and cause.
     * @param response the HTTP response
     * @param serviceError the service error
     * @param throwable the cause, or null
     */
    public ClientInternalServerException(HttpResponse<String> response, ServiceErrorModel serviceError, Throwable throwable) {
        super(response, serviceError, throwable);
    }

    @Override
    public String toString() {
        return "ClientInternalServerException{} " + super.toString();
    }
}
