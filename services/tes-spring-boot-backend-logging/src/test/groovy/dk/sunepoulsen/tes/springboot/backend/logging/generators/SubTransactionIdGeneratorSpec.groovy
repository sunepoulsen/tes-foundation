package dk.sunepoulsen.tes.springboot.backend.logging.generators

import spock.lang.Specification

class SubTransactionIdGeneratorSpec extends Specification {

    void "Tests generator"() {
        given:
            SubTransactionIdGenerator sut = new SubTransactionIdGenerator('id')

        expect:
            sut.generate() == 'id:1'
            sut.generate() == 'id:2'
            sut.generate() == 'id:3'
            sut.generate() == 'id:4'
            sut.generate() == 'id:5'
    }

}
