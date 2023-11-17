package dk.sunepoulsen.tes.springboot.rest.logic

import spock.lang.Specification
import spock.lang.Unroll

class PatchUtilitiesSpec extends Specification {
    @Unroll
    void "#_description"() {
        when:
            Integer result = PatchUtilities.patchValue(_oldValue, _newValue)

        then:
            result == _expected

        where:
            _oldValue | _newValue | _expected | _description
            null      | null      | null      | 'Patch null value'
            5         | null      | 5         | 'Patch with no new value'
            5         | 7         | 7         | 'Patch with new value'
            null      | 7         | 7         | 'Patch null value with new value'
    }
}
