package dk.sunepoulsen.tes.testdata.generators

import spock.lang.Specification

class TestDataSupplierSpec extends Specification {

    void "Test of TestDataSupplier"() {
        given:
            TestDataSupplier<Long> sut = new TestDataSupplier<>({
                45L
            })

        when:
            List<Long> results = (0..100).collect {sut.generate()}

        then:
            results == (0..100).collect {45L}
    }

}
