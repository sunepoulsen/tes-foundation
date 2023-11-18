package dk.sunepoulsen.tes.rest.integrations

import dk.sunepoulsen.tes.rest.integrations.exceptions.ClientBadRequestException
import dk.sunepoulsen.tes.rest.models.ServiceErrorModel
import dk.sunepoulsen.tes.rest.models.monitoring.*
import spock.lang.Specification

import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

class TechEasySolutionsBackendIntegratorSpec extends Specification {

    private TechEasySolutionsClient httpClient
    private TechEasySolutionsBackendIntegrator sut

    void setup() {
        this.httpClient = Mock(TechEasySolutionsClient)
        this.sut = new TechEasySolutionsBackendIntegrator(this.httpClient)
    }

    void "Get health with OK"() {
        when:
            ServiceHealth result = this.sut.health().blockingGet()

        then:
            1 * httpClient.get('/actuator/health', ServiceHealth) >> CompletableFuture.supplyAsync { new ServiceHealth(status: ServiceHealthStatusCode.UP) }
            result == new ServiceHealth(status: ServiceHealthStatusCode.UP)
    }

    void "Get health with BadRequest result"() {
        when:
            this.sut.health().blockingGet()

        then:
            1 * httpClient.get('/actuator/health', ServiceHealth) >> CompletableFuture.supplyAsync {
                throw new ExecutionException("message", new ClientBadRequestException(Mock(HttpResponse), new ServiceErrorModel(
                    code: 'code',
                    param: 'param',
                    message: 'message'
                )))
            }
            ClientBadRequestException ex = thrown(ClientBadRequestException)
            ex.serviceError.code == 'code'
            ex.serviceError.param == 'param'
            ex.serviceError.message == 'message'
    }

    void "Get info with OK"() {
        given:
            ServiceInfo expected = new ServiceInfo(
                app: new ServiceInfoApp(
                    name: 'name',
                    version: '0.1.0-SNAPSHOT',
                    service: new ServiceInfoService(
                        name: 'service-name'
                    )
                )
            )

        when:
            ServiceInfo result = this.sut.info().blockingGet()

        then:
            1 * httpClient.get('/actuator/info', ServiceInfo) >> CompletableFuture.supplyAsync { expected }
            result == expected
    }

    void "Get info with BadRequest result"() {
        when:
            this.sut.info().blockingGet()

        then:
            1 * httpClient.get('/actuator/info', ServiceInfo) >> CompletableFuture.supplyAsync {
                throw new ExecutionException("message", new ClientBadRequestException(Mock(HttpResponse), new ServiceErrorModel(
                    code: 'code',
                    param: 'param',
                    message: 'message'
                )))
            }
            ClientBadRequestException ex = thrown(ClientBadRequestException)
            ex.serviceError.code == 'code'
            ex.serviceError.param == 'param'
            ex.serviceError.message == 'message'
    }

}
