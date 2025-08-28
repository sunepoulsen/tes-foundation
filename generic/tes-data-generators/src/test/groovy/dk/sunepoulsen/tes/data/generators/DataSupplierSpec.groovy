package dk.sunepoulsen.tes.data.generators

import spock.lang.Specification

class DataSupplierSpec extends Specification {

    void "Test of TestDataSupplier"() {
        given:
            DataSupplier<Long> sut = new DataSupplier<>({
                45L
            })

        when:
            List<Long> results = (0..100).collect {sut.generate()}

        then:
            results == (0..100).collect {45L}
    }

}
