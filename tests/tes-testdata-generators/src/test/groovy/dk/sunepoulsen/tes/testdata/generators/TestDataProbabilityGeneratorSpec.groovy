package dk.sunepoulsen.tes.testdata.generators

import spock.lang.Specification

class TestDataProbabilityGeneratorSpec extends Specification {

    void "Test Generators: Probability Generator"() {
        given:
            int entries = 10000
            TestDataProbabilityGenerator<Double> sut = new TestDataProbabilityGenerator<Double>([
                new TestDataProbabilityGeneratorItem<>(20.0, NumberGenerators.doubleGenerator(500.0, 10000.0)),
                new TestDataProbabilityGeneratorItem<>(30.0, NumberGenerators.doubleGenerator(10000.0, 100000.0)),
                new TestDataProbabilityGeneratorItem<>(50.0, NumberGenerators.doubleGenerator(100000.0, 200000.0))
            ])

        when:
            List<Double> results = (1..entries).collect {
                sut.generate()
            }

            List<Double> resultsInterval1 = results
                .findAll {500.0 <= it && it <= 10000.0}
            List<Double> resultsInterval2 = results
                .findAll {10000.0 <= it && it <= 100000.0}
            List<Double> resultsInterval3 = results
                .findAll {100000.0 <= it && it <= 200000.0}

        then:
            results.size() == entries
            resultsInterval1.size() / results.size() * 100.0 < 21.0
            resultsInterval2.size() / results.size() * 100.0 < 31.0
            resultsInterval3.size() / results.size() * 100.0 < 51.0
    }

}
