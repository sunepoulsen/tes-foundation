package dk.sunepoulsen.tes.json.data.generators

import dk.sunepoulsen.tes.data.generators.DataGenerator
import spock.lang.Specification

class JsonDataGeneratorSpec extends Specification {

    void "Serialize the generated value as JSON"() {
        given:
            DataGenerator<Object> generator = Stub(DataGenerator) {
                generate() >> value
            }
            JsonDataGenerator<Object> sut = new JsonDataGenerator<>(generator)

        expect:
            sut.generate() == expected

        where:
            value                        | expected
            [name: 'Alice', count: 2]    | '{"name":"Alice","count":2}'
            'hello "world"\n'            | '"hello \\"world\\"\\n"'
            null                         | 'null'
    }

    void "Generate a fresh value on each call"() {
        given:
            DataGenerator<String> generator = Mock(DataGenerator)
            JsonDataGenerator<String> sut = new JsonDataGenerator<>(generator)

        when:
            String first = sut.generate()
            String second = sut.generate()

        then:
            2 * generator.generate() >>> ['first', 'second']
            first == '"first"'
            second == '"second"'
    }

    void "Propagate generator exceptions"() {
        given:
            RuntimeException failure = new RuntimeException('generation failed')
            DataGenerator<Object> generator = Stub(DataGenerator) {
                generate() >> { throw failure }
            }
            JsonDataGenerator<Object> sut = new JsonDataGenerator<>(generator)

        when:
            sut.generate()

        then:
            RuntimeException thrownException = thrown()
            thrownException.is(failure)
    }
}