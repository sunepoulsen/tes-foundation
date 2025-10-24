package dk.sunepoulsen.tes.sut.engine.services

import spock.lang.Specification

class ContainerSecureProtocolSpec extends Specification {

    private ContainerSecureProtocol sut

    void setup() {
        this.sut = new ContainerSecureProtocol()
    }

    void "Test exposed port"() {
        expect:
            sut.exposedPort() == 8080
    }

    void "Test base URI"() {
        expect:
            sut.baseUrl('localhost', 4586) == new URI('https://localhost:4586')
    }
}
