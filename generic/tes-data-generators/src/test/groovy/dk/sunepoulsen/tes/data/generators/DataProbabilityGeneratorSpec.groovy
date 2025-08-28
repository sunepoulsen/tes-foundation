package dk.sunepoulsen.tes.data.generators

import spock.lang.Specification

class DataProbabilityGeneratorSpec extends Specification {

    void "Test Generators: Probability Generator"() {
        given:
            int entries = 250000
            DataProbabilityGenerator<Double> sut = new DataProbabilityGenerator<Double>([
                new DataProbabilityGeneratorItem<>(20.0, NumberGenerators.doubleGenerator(500.0, 10000.0)),
                new DataProbabilityGeneratorItem<>(30.0, NumberGenerators.doubleGenerator(10000.0, 100000.0)),
                new DataProbabilityGeneratorItem<>(50.0, NumberGenerators.doubleGenerator(100000.0, 200000.0))
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

            double percentageInterval1 = resultsInterval1.size() / results.size() * 100.0
            double percentageInterval2 = resultsInterval2.size() / results.size() * 100.0
            double percentageInterval3 = resultsInterval3.size() / results.size() * 100.0

        then:
            results.size() == entries
            percentageInterval1 <= 20.5
            percentageInterval2 <= 30.5
            percentageInterval3 <= 50.5
    }

}
