package dk.sunepoulsen.tes.data.generators

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
            _testcase               | _generator                           | _entries | _expectedValue
            'Supplier generator'    | Generators.supplierGenerator { 45L } | 100      | 45L
            'Fixed value generator' | Generators.fixedGenerator('45.56')   | 100      | '45.56'
    }

    void "Test Generators: enumGenerator"() {
        given:
            int entries = 10000
            DataGenerator<String> sut = Generators.enumGenerator(Thread.State)

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
            DataGenerator<String> sut = Generators.textGenerator(NumberGenerators.integerGenerator(10, 30))

        when:
            def results = (1..entries).collect {
                sut.generate()
            }

        then:
            results.size() == entries
            results.stream()
                .filter {!it.empty }
                .distinct()
                .count() == entries
            results.stream().allMatch { !(it.length() < 10) }
            results.stream().allMatch { !(it.length() > 30) }
    }

    void "Test Generators: textGenerator with characters"() {
        given:
            int entries = 10000
            List<String> characters = List.of(CharacterGenerator.ALPHA, CharacterGenerator.ALPHA.toLowerCase())
            DataGenerator<String> sut = Generators.textGenerator(characters, NumberGenerators.integerGenerator(10, 30))

        when:
            def results = (1..entries).collect {
                sut.generate()
            }

        then:
            results.size() == entries
            results.stream()
                .filter {!it.empty }
                .distinct()
                .count() == entries
            results.stream().allMatch { !(it.length() < 10) }
            results.stream().allMatch { !(it.length() > 30) }
    }

    void "Test Generators: textGenerator with CharacterGenerator"() {
        given:
            int entries = 10000
            DataGenerator<String> sut = Generators.textGenerator(CharacterGenerator.createAlpha(), NumberGenerators.integerGenerator(10, 30))

        when:
            def results = (1..entries).collect {
                sut.generate()
            }

        then:
            results.size() == entries
            results.stream()
                .filter {!it.empty }
                .distinct()
                .count() == entries
            results.stream().allMatch { !(it.length() < 10) }
            results.stream().allMatch { !(it.length() > 30) }
    }

    void "Test Generators: passwordGenerator"() {
        given:
            int entries = 10000
            DataGenerator<String> sut = Generators.passwordGenerator()

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

    void "Test Generators: uuidGenerator"() {
        given:
            int entries = 10000
            DataGenerator<UUID> sut = Generators.uuidGenerator()

        when:
            List<UUID> results = (1..entries).collect {
                sut.generate()
            }

        then:
            results.size() == entries
            results.stream()
                .map { it.toString() }
                .filter {!it.empty }
                .distinct()
                .count() == entries
    }

}
