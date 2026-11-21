package dk.sunepoulsen.tes.rest.integrations

import dk.sunepoulsen.tes.rest.integrations.exceptions.ClientException
import dk.sunepoulsen.tes.rest.models.ServiceErrorModel
import spock.lang.Specification

class AbstractIntegratorSpec extends Specification {

    private TechEasySolutionsClient httpClient
    private AbstractIntegrator sut

    void setup() {
        this.httpClient = Mock(TechEasySolutionsClient)
        this.sut = new AbstractIntegrator(this.httpClient)
    }

    void "Constructor retains the HTTP client"() {
        expect:
            this.sut.httpClient.is(this.httpClient)
    }

    void "Exception mapping preserves the first client exception for both reactive types"() {
        given:
            Throwable root = new IllegalArgumentException('root')
            ClientException client = new ClientException(new ServiceErrorModel(message: 'client'), root)
            Throwable input = wrapped ? new RuntimeException('outer', new RuntimeException('inner', client)) : client

        expect:
            this.sut.mapClientExceptions(input).test().assertError { Throwable error -> error.is(client) }
            this.sut.mapClientExceptionsAsCompletable(input).test().assertError { Throwable error -> error.is(client) }

        where:
            wrapped << [false, true]
    }

    void "Exception mapping returns the deepest non-client exception for both reactive types"() {
        given:
            Throwable root = new IllegalArgumentException('root')
            Throwable input = wrapped ? new RuntimeException('outer', new RuntimeException('inner', root)) : root

        expect:
            this.sut.mapClientExceptions(input).test().assertError { Throwable error -> error.is(root) }
            this.sut.mapClientExceptionsAsCompletable(input).test().assertError { Throwable error -> error.is(root) }

        where:
            wrapped << [false, true]
    }

    void "Build HTTP query from parameters"() {
        expect:
            this.sut.createHttpQuery(params) == expected

        where:
            params                               | expected
            [:]                                  | ''
            [name: 'value']                      | '?name=value'
            [first: 'hello world', second: 'æ&='] | '?first=hello+world&second=%C3%A6%26%3D'
            [empty: '']                          | '?empty='
    }
}