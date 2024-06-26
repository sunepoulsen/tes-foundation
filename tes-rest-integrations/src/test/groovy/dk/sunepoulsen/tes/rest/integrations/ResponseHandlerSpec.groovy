package dk.sunepoulsen.tes.rest.integrations

import dk.sunepoulsen.tes.json.exceptions.DecodeJsonException
import dk.sunepoulsen.tes.rest.integrations.exceptions.*
import dk.sunepoulsen.tes.rest.models.monitoring.ServiceHealthStatusCode
import groovy.json.JsonOutput
import spock.lang.Specification
import spock.lang.Unroll

import java.net.http.HttpResponse

class ResponseHandlerSpec extends Specification {

    private ResponseHandler sut

    void setup() {
        this.sut = new ResponseHandler()
    }

    void "Verify response and extract body for a valid body"() {
        given:
            HttpResponse<String> response = Mock(HttpResponse)
            String body = JsonOutput.toJson(
                [
                    status: ServiceHealthStatusCode.UP.toString()
                ])

        when:
            String result = this.sut.verifyResponseAndExtractBody(response)

        then:
            result == body
            2 * response.statusCode() >> 200
            1 * response.body() >> body
            0 * response._
    }

    @Unroll
    void "Verify response and extract body for #_description"() {
        given:
            HttpResponse<String> response = Mock(HttpResponse)
            String body = JsonOutput.toJson(
                [
                    code: 'code',
                    message: 'message'
                ])

        when:
            this.sut.verifyResponseAndExtractBody(response)

        then:
            ClientResponseException ex = thrown(_exception)
            ex.serviceError.code == 'code'
            ex.serviceError.message == 'message'
            3 * response.statusCode() >> _respponseCode
            1 * response.body() >> body
            0 * response._

        where:
            _respponseCode | _exception                    | _description
            400            | ClientBadRequestException | 'a bad request'
            404            | ClientNotFoundException | 'a not found response'
            409            | ClientConflictException | 'a conflict response'
            500            | ClientInternalServerException | 'an internal server error response'
            501            | ClientNotImplementedException | 'a not implemented response'
            999            | ClientResponseException       | 'an unknown response'
    }

    @Unroll
    void "Verify response and extract body for #_description with an unknown body type"() {
        given:
            HttpResponse<String> response = Mock(HttpResponse)
            String body = 'no json'

        when:
            this.sut.verifyResponseAndExtractBody(response)

        then:
            thrown(DecodeJsonException)
            3 * response.statusCode() >> _respponseCode
            1 * response.body() >> body
            0 * response._

        where:
            _respponseCode | _description
            400            | 'a bad request'
            404            | 'a not found response'
            409            | 'a conflict response'
            500            | 'an internal server error response'
            501            | 'a not implemented response'
            999            | 'an unknown response'
    }

}
