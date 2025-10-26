package dk.sunepoulsen.tes.rest.models.validation.constraints

import spock.lang.Specification
import spock.lang.Unroll

import java.util.regex.Pattern

class UriPathPatternSpec extends Specification {

    @Unroll
    void "Test Uri Path Pattern for characters: #_input"() {
        given:
            Pattern pattern = Pattern.compile(UriPathPattern.URI_PATTERN)

        expect:
            pattern.matcher(_input).matches() == _expected

        where:
            _input  | _expected
            ' '     | false
            ';'     | false
            ' ;'    | false
            'Alpha' | true
            '%3D'   | true
    }

}
