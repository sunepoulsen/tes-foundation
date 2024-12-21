package dk.sunepoulsen.tes.testdata.generators

import spock.lang.Specification
import spock.lang.Unroll

class CharacterGeneratorSpec extends Specification {

    private static final int ENTRIES = 10000

    private static final ALPHA_CHARS = [CharacterGenerator.ALPHA, CharacterGenerator.ALPHA.toLowerCase()]
    private static final DIGIT_CHARS = [CharacterGenerator.DIGITS]
    private static final ALL_CHARS = [ALPHA_CHARS.join(''),
                                      CharacterGenerator.DIGITS,
                                      CharacterGenerator.SPECIAL
    ]

    @Unroll
    void "Test generate(): #_testcase"() {
        given:
            String expectedCharSet = _charSet.join('')

        when:
            List<Character> result = (1..ENTRIES).collect {
                _generator.generate()
            }

        then:
            result.size() == ENTRIES
            result.stream().anyMatch { it != result.first }
            result.each {
                assert expectedCharSet.contains(new String(it))
            }

        where:
            _testcase                  | _generator                        | _charSet
            'Alpha numeric characters' | CharacterGenerator.createAlpha()  | ALPHA_CHARS
            'Digit characters'         | CharacterGenerator.createDigits() | DIGIT_CHARS
            'All characters'           | CharacterGenerator.createAll()    | ALL_CHARS
    }
}
