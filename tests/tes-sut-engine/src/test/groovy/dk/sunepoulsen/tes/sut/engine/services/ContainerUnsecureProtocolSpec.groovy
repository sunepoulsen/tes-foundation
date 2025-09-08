package dk.sunepoulsen.tes.sut.engine.services

import spock.lang.Specification

class ContainerUnsecureProtocolSpec extends Specification {

    private ContainerUnsecureProtocol sut

    void setup() {
        this.sut = new ContainerUnsecureProtocol()
    }

    void "Test exposed port"() {
        expect:
            sut.exposedPort() == 8080
    }

    void "Test base URI"() {
        expect:
            sut.baseUrl('localhost', 4586) == new URI('http://localhost:4586')
    }
}
