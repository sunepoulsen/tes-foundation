package dk.sunepoulsen.tes.testdata.generators

import spock.lang.Specification
import spock.lang.Unroll

class GeneratorsSpec extends Specification {

    @Unroll
    void "Test Generators: #_testcase"() {
        when:
            def results = (0.._entries).collect { _generator.generate() }

        then:
            results == (0.._entries).collect { _expectedValue }

        where:
            _testcase            | _generator                           | _entries | _expectedValue
            'Supplier generator' | Generators.supplierGenerator { 45L } | 100      | 45L
            'Fixed generator'    | Generators.fixedGenerator('45.56')   | 100      | '45.56'
    }

    void "Test Generators: enumGenerator"() {
        given:
            int entries = 10000
            TestDataGenerator<String> sut = Generators.enumGenerator(Thread.State)

        when:
            def results = (1..entries).collect {
                sut.generate()
            }

        then:
            results.size() == entries
            results.stream().anyMatch { it == Thread.State.NEW.name() }
            results.stream().anyMatch { it == Thread.State.RUNNABLE.name() }
            results.stream().anyMatch { it == Thread.State.BLOCKED.name() }
            results.stream().anyMatch { it == Thread.State.WAITING.name() }
            results.stream().anyMatch { it == Thread.State.TIMED_WAITING.name() }
            results.stream().anyMatch { it == Thread.State.TERMINATED.name() }
    }

    void "Test Generators: textGenerator with length"() {
        given:
            int entries = 10000
            TestDataGenerator<String> sut = Generators.textGenerator(NumberGenerators.integerGenerator(10, 30))

        when:
            def results = (1..entries).collect {
                sut.generate()
            }

        then:
            results.size() == entries
            results.stream().anyMatch { it != results.first }
            results.stream().allMatch { !it.empty }
            results.stream().allMatch { !(it.length() < 10) }
            results.stream().allMatch { !(it.length() > 30) }
    }

    void "Test Generators: textGenerator with characters"() {
        given:
            int entries = 10000
            TestDataGenerator<String> sut = Generators.textGenerator(["abc"], NumberGenerators.integerGenerator(10, 30))

        when:
            def results = (1..entries).collect {
                sut.generate()
            }

        then:
            results.size() == entries
            results.stream().anyMatch { it != results.first }
            results.stream().allMatch { !it.empty }
            results.stream().allMatch { it.findAll(/[^a-c]/).empty }
            results.stream().allMatch { !(it.length() < 10) }
            results.stream().allMatch { !(it.length() > 30) }
    }

    void "Test Generators: textGenerator with CharacterGenerator"() {
        given:
            int entries = 10000
            TestDataGenerator<String> sut = Generators.textGenerator(new CharacterGenerator(["abcde"]), NumberGenerators.integerGenerator(10, 30))

        when:
            def results = (1..entries).collect {
                sut.generate()
            }

        then:
            results.size() == entries
            results.stream().anyMatch { it != results.first }
            results.stream().allMatch { !it.empty }
            results.stream().allMatch { it.findAll(/[^a-e]/).empty }
            results.stream().allMatch { !(it.length() < 10) }
            results.stream().allMatch { !(it.length() > 30) }
    }

    void "Test Generators: passwordGenerator"() {
        given:
            int entries = 10000
            TestDataGenerator<String> sut = Generators.passwordGenerator()

        when:
            def results = (1..entries).collect {
                sut.generate()
            }

        then:
            results.size() == entries
            results.stream().anyMatch { it != results.first }
            results.stream().allMatch { !it.empty }
            results.stream().allMatch { !(it.length() < Generators.PASSWORD_MIN_LENGTH) }
            results.stream().allMatch { !(it.length() > Generators.PASSWORD_MAX_LENGTH) }

    }

}
