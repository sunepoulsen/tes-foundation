package dk.sunepoulsen.tes.springboot.backend.logging

import dk.sunepoulsen.tes.data.generators.FixedDataGenerator
import dk.sunepoulsen.tes.data.generators.Generators
import dk.sunepoulsen.tes.springboot.backend.logging.exceptions.RequestTransactionInvalidException
import spock.lang.Specification

class RequestTransactionServiceSpec extends Specification {

    private RequestTransactionService sut

    void setup() {
        this.sut = new RequestTransactionService()
    }

    void "Test generate id's of initialized instance"() {
        given:
            FixedDataGenerator<UUID> operationIdGenerator = Generators.fixedGenerator(UUID.randomUUID())

        when:
            sut.initialize(operationIdGenerator, Generators.fixedGenerator('transactionId'))

        then:
            sut.operationId().generate() == operationIdGenerator.generate()
            sut.transactionId().generate() == 'transactionId'
            sut.subTransactionId().generate() == 'transactionId:1'
    }

    void "Test invalidating of initialized instance"() {
        given:
            FixedDataGenerator<UUID> operationIdGenerator = Generators.fixedGenerator(UUID.randomUUID())

        and:
            sut.initialize(operationIdGenerator, Generators.fixedGenerator('transactionId'))

        when:
            sut.invalidate()

        then:
            !sut.valid
    }

    void "Test get operationId of uninitialized instance"() {
        when:
            sut.operationId()

        then:
            thrown(RequestTransactionInvalidException)
    }

    void "Test get transactionId of uninitialized instance"() {
        when:
            sut.transactionId()

        then:
            thrown(RequestTransactionInvalidException)
    }

    void "Test get subTransactionId of uninitialized instance"() {
        when:
            sut.subTransactionId()

        then:
            thrown(RequestTransactionInvalidException)
    }

}
