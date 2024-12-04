package dk.sunepoulsen.tes.rest.models.monitoring

import dk.sunepoulsen.tes.rest.models.testutils.ConstraintViolationAssertions
import dk.sunepoulsen.tes.rest.models.testutils.TestData
import dk.sunepoulsen.tes.rest.models.validation.DefaultValidator
import jakarta.validation.ConstraintViolationException
import spock.lang.Specification

/**
 * Test of the validation of {@cpde ServiceHealth}
 */
class ServiceHealthSpec extends Specification {

    private DefaultValidator validator

    void setup() {
        this.validator = new DefaultValidator()
    }

    void "Validate a valid ServiceHealth"() {
        expect:
            this.validator.validate(new ServiceHealth(status: ServiceHealthStatusCode.UP))
    }

    void "Validate an invalid ServiceHealth with a null status"() {
        when:
            this.validator.validate(new ServiceHealth())

        then:
            ConstraintViolationException exception = thrown(ConstraintViolationException)
            ConstraintViolationAssertions.verifyViolations(exception.constraintViolations, [
                TestData.createError('status', 'must not be null')
            ])
    }
}
