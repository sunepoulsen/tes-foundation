package dk.sunepoulsen.tes.testdata.generators

import spock.lang.Specification

class TestDataListGeneratorSpec extends Specification {

    private static final int ENTRIES = 100

    void "Test TestDataListGenerator with fixed number of items"() {
        given:
            TestDataListGenerator<Long> sut = new TestDataListGenerator<>(Generators.fixedGenerator(ENTRIES), {45L})

        when:
            List<Long> results = sut.generate()

        then:
            results.size() == ENTRIES
            results == (1..ENTRIES).collect {45L}
    }

    void "Test TestDataListGenerator with interval of items"() {
        given:
            TestDataListGenerator<Long> sut = new TestDataListGenerator<>(NumberGenerators.integerGenerator(50, 100), {45L})

        when:
            List<Long> results = sut.generate()

        then:
            results.size() >= 50
            results.size() < 100
            results == (1..results.size()).collect {45L}
    }

}
