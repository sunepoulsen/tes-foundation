package dk.sunepoulsen.tes.rest.models

import dk.sunepoulsen.tes.rest.models.testutils.ConstraintViolationAssertions
import dk.sunepoulsen.tes.rest.models.testutils.TestData
import dk.sunepoulsen.tes.rest.models.validation.DefaultValidator
import jakarta.validation.ConstraintViolationException
import spock.lang.Specification

class RangeModelSpec extends Specification {

    private DefaultValidator validator

    void setup() {
        this.validator = new DefaultValidator()
    }

    void "Validate a RangeModel with valid values"() {
        given:
            RangeModel<Integer> model = new RangeModel<Integer>(
                min: 0,
                max: 45
            )

        expect:
            this.validator.validate(model)
    }

    void "Validate a RangeModel with negative min and max"() {
        given:
            RangeModel<Integer> model = new RangeModel(
                min: -10,
                max: -5
            )

        when:
            this.validator.validate(model)

        then:
            ConstraintViolationException exception = thrown(ConstraintViolationException)
            ConstraintViolationAssertions.verifyViolations(exception.constraintViolations, [
                TestData.createError('min', 'must be greater than or equal to 0'),
                TestData.createError('max', 'must be greater than or equal to 0')
            ])
    }

    void "Validate a RangeModel with min > max"() {
        given:
            RangeModel<Integer> model = new RangeModel(
                min: 20,
                max: 15
            )

        when:
            this.validator.validate(model)

        then:
            ConstraintViolationException exception = thrown(ConstraintViolationException)
            ConstraintViolationAssertions.verifyViolations(exception.constraintViolations, [
                TestData.createError('', 'max must be equal or greater than min')
            ])
    }
}
