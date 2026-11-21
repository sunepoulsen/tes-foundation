package dk.sunepoulsen.tes.rest.integrations;

import dk.sunepoulsen.tes.json.JsonMapper;
import dk.sunepoulsen.tes.rest.integrations.exceptions.*;
import dk.sunepoulsen.tes.rest.models.ServiceErrorModel;
import dk.sunepoulsen.tes.rest.models.ServiceValidationErrorModel;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpResponse;

/**
 * Extracts successful HTTP response bodies and maps error responses to client exceptions.
 */
@Slf4j
public class ResponseHandler {

    /** Log message used when mapping a response body to an exception. */
    private static final String RESPONSE_EXCEPTION_LOG_MESSAGE = "Extract {} from body to {} exception";

    /** Mapper used to decode error response bodies. */
    private final JsonMapper jsonMapper;

    /**
     * Creates a response handler.
     *
     * @param jsonMapper mapper used to decode error response bodies
     */
    public ResponseHandler(final JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    /**
     * Returns the body of a successful response or throws an exception for an error response.
     *
     * @param response HTTP response to inspect
     * @return the response body for a 2xx status
     * @throws ClientResponseException if the response status is not successful and its error body is decoded
     */
    public String verifyResponseAndExtractBody(HttpResponse<String> response) {
        log.debug("Receive response of request {} {} with status code {}", response.request().method(), response.request().uri(), response.statusCode());
        log.trace("Receive body of request: {}", response.body());
        if( response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.body();
        }

        switch(response.statusCode()) {
            case 400:
                log.trace(RESPONSE_EXCEPTION_LOG_MESSAGE, ServiceValidationErrorModel.class.getName(), ClientBadRequestException.class.getName());
                throw new ClientBadRequestException(response, decodeResponseBody(response.body(), response.statusCode(), ServiceValidationErrorModel.class));

            case 401:
                log.trace(RESPONSE_EXCEPTION_LOG_MESSAGE, ServiceErrorModel.class.getName(), ClientUnauthorizedException.class.getName());
                throw new ClientUnauthorizedException(response, decodeResponseBody(response.body(), response.statusCode(), ServiceErrorModel.class));

            case 403:
                log.trace(RESPONSE_EXCEPTION_LOG_MESSAGE, ServiceErrorModel.class.getName(), ClientForbiddenException.class.getName());
                throw new ClientForbiddenException(response, decodeResponseBody(response.body(), response.statusCode(), ServiceErrorModel.class));

            case 404:
                log.trace(RESPONSE_EXCEPTION_LOG_MESSAGE, ServiceErrorModel.class.getName(), ClientNotFoundException.class.getName());
                throw new ClientNotFoundException(response, decodeResponseBody(response.body(), response.statusCode(), ServiceErrorModel.class));

            case 409:
                log.trace(RESPONSE_EXCEPTION_LOG_MESSAGE, ServiceErrorModel.class.getName(), ClientConflictException.class.getName());
                throw new ClientConflictException(response, decodeResponseBody(response.body(), response.statusCode(), ServiceErrorModel.class));

            case 500:
                log.trace(RESPONSE_EXCEPTION_LOG_MESSAGE, ServiceErrorModel.class.getName(), ClientInternalServerException.class.getName());
                throw new ClientInternalServerException(response, decodeResponseBody(response.body(), response.statusCode(), ServiceErrorModel.class));

            case 501:
                log.trace(RESPONSE_EXCEPTION_LOG_MESSAGE, ServiceErrorModel.class.getName(), ClientNotImplementedException.class.getName());
                throw new ClientNotImplementedException(response, decodeResponseBody(response.body(), response.statusCode(), ServiceErrorModel.class));

            default:
                log.trace("Extract {} from body to general {} exception", ServiceErrorModel.class.getName(), ClientResponseException.class.getName());
                throw new ClientResponseException(response, decodeResponseBody(response.body(), response.statusCode(), ServiceErrorModel.class));
        }
    }

    private ServiceErrorModel decodeResponseBody(String responseBody, int responseStatusCode, Class<? extends ServiceErrorModel> clazz) {
        if (responseBody != null && !responseBody.isEmpty()) {
            return this.jsonMapper.decode(responseBody, clazz);
        }

        ServiceErrorModel model = new ServiceErrorModel();
        model.setMessage("Service returned response with status %s".formatted(responseStatusCode));

        return model;
    }

}
