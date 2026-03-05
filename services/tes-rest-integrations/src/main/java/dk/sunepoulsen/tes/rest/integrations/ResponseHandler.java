package dk.sunepoulsen.tes.rest.integrations;

import dk.sunepoulsen.tes.json.JsonMapper;
import dk.sunepoulsen.tes.rest.integrations.exceptions.*;
import dk.sunepoulsen.tes.rest.models.ServiceErrorModel;
import dk.sunepoulsen.tes.rest.models.ServiceValidationErrorModel;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpResponse;

@Slf4j
public class ResponseHandler {

    private static final String RESPONSE_EXCEPTION_LOG_MESSAGE = "Extract {} from body to {} exception";

    private final JsonMapper jsonMapper;

    public ResponseHandler(final JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public String verifyResponseAndExtractBody(HttpResponse<String> response) {
        log.trace("Receive status code {} of request", response.statusCode());
        log.trace("Receive body of request: {}", response.body());
        if( response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.body();
        }

        switch(response.statusCode()) {
            case 400:
                log.trace(RESPONSE_EXCEPTION_LOG_MESSAGE, ServiceValidationErrorModel.class.getName(), ClientBadRequestException.class.getName());
                throw new ClientBadRequestException(response, this.jsonMapper.decode(response.body(), ServiceValidationErrorModel.class));

            case 401:
                log.trace(RESPONSE_EXCEPTION_LOG_MESSAGE, ServiceErrorModel.class.getName(), ClientUnauthorizedException.class.getName());
                throw new ClientUnauthorizedException(response, this.jsonMapper.decode(response.body(), ServiceErrorModel.class));

            case 404:
                log.trace(RESPONSE_EXCEPTION_LOG_MESSAGE, ServiceErrorModel.class.getName(), ClientNotFoundException.class.getName());
                throw new ClientNotFoundException(response, this.jsonMapper.decode(response.body(), ServiceErrorModel.class));

            case 409:
                log.trace(RESPONSE_EXCEPTION_LOG_MESSAGE, ServiceErrorModel.class.getName(), ClientConflictException.class.getName());
                throw new ClientConflictException(response, this.jsonMapper.decode(response.body(), ServiceErrorModel.class));

            case 500:
                log.trace(RESPONSE_EXCEPTION_LOG_MESSAGE, ServiceErrorModel.class.getName(), ClientInternalServerException.class.getName());
                throw new ClientInternalServerException(response, this.jsonMapper.decode(response.body(), ServiceErrorModel.class));

            case 501:
                log.trace(RESPONSE_EXCEPTION_LOG_MESSAGE, ServiceErrorModel.class.getName(), ClientNotImplementedException.class.getName());
                throw new ClientNotImplementedException(response, this.jsonMapper.decode(response.body(), ServiceErrorModel.class));

            default:
                log.trace("Extract {} from body to general {} exception", ServiceErrorModel.class.getName(), ClientResponseException.class.getName());
                throw new ClientResponseException(response, this.jsonMapper.decode(response.body(), ServiceErrorModel.class));
        }
    }

}
