package dk.sunepoulsen.tes.data.generators

import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.Field
import java.security.SecureRandom

class DataProbabilityGeneratorSpec extends Specification {

    @Unroll
    void "Reject probabilities totaling #total"() {
        given:
            DataGenerator<String> generator = Mock()
            List<DataProbabilityGeneratorItem<String>> items = probabilities.collect { double probability ->
                new DataProbabilityGeneratorItem<String>(probability, generator)
            }
            DataProbabilityGenerator<String> sut = new DataProbabilityGenerator<String>(items)

        when:
            sut.generate()

        then:
            IllegalArgumentException exception = thrown()
            exception.message == 'Probabilities does not sum to 100'
            0 * generator.generate()

        where:
            probabilities | total
            []            | 0
            [20.0, 30.0]  | 50
            [60.0, 50.0]  | 110
    }

    @Unroll
    void "Select generator for probability #probability"() {
        given:
            DataGenerator<String> first = Mock()
            DataGenerator<String> second = Mock()
            DataGenerator<String> third = Mock()
            DataProbabilityGenerator<String> sut = new DataProbabilityGenerator<String>([
                new DataProbabilityGeneratorItem<String>(20.0, first),
                new DataProbabilityGeneratorItem<String>(30.0, second),
                new DataProbabilityGeneratorItem<String>(50.0, third)
            ])
            SecureRandom random = Stub()
            random.nextDouble(0.0, 100.0) >> probability
            Field randomField = DataProbabilityGenerator.getDeclaredField('random')
            randomField.setAccessible(true)
            randomField.set(sut, random)

        when:
            String result = sut.generate()

        then:
            firstCalls * first.generate() >> 'first'
            secondCalls * second.generate() >> 'second'
            thirdCalls * third.generate() >> 'third'
            result == expected

        where:
            probability               | expected | firstCalls | secondCalls | thirdCalls
            Math.nextUp(0.0d)          | 'first'  | 1          | 0           | 0
            Math.nextDown(20.0d)       | 'first'  | 1          | 0           | 0
            Math.nextUp(20.0d)         | 'second' | 0          | 1           | 0
            Math.nextDown(50.0d)       | 'second' | 0          | 1           | 0
            Math.nextUp(50.0d)         | 'third'  | 0          | 0           | 1
            Math.nextDown(100.0d)      | 'third'  | 0          | 0           | 1
    }

    @Unroll
    void "Reject exact interval boundary #probability"() {
        given:
            DataGenerator<String> generator = Mock()
            DataProbabilityGenerator<String> sut = new DataProbabilityGenerator<String>([
                new DataProbabilityGeneratorItem<String>(20.0, generator),
                new DataProbabilityGeneratorItem<String>(30.0, generator),
                new DataProbabilityGeneratorItem<String>(50.0, generator)
            ])
            SecureRandom random = Stub()
            random.nextDouble(0.0, 100.0) >> probability
            Field randomField = DataProbabilityGenerator.getDeclaredField('random')
            randomField.setAccessible(true)
            randomField.set(sut, random)

        when:
            sut.generate()

        then:
            IllegalArgumentException exception = thrown()
            exception.message == 'Probabilities are out of range'
            0 * generator.generate()

        where:
            probability << [0.0d, 20.0d, 50.0d]
    }

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
