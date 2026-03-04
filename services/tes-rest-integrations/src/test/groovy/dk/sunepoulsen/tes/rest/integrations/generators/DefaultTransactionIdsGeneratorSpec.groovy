package dk.sunepoulsen.tes.rest.integrations.generators

import spock.lang.Specification

class DefaultTransactionIdsGeneratorSpec extends Specification {

    void "Next operation id"() {
        given:
            DefaultTransactionIdsGenerator sut = new DefaultTransactionIdsGenerator()

        when:
            List<UUID> results = [sut.operationId().generate(), sut.operationId().generate()]

        then:
            results.first != results.last
    }

    void "Next transaction id"() {
        given:
            DefaultTransactionIdsGenerator sut = new DefaultTransactionIdsGenerator()

        when:
            List<String> results = [sut.transactionId().generate(), sut.transactionId().generate()]

        then:
            results.first != results.last
    }

}
