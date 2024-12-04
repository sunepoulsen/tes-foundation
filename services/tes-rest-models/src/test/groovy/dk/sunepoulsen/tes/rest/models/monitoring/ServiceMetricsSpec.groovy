package dk.sunepoulsen.tes.rest.models.monitoring

import dk.sunepoulsen.tes.rest.models.testutils.ConstraintViolationAssertions
import dk.sunepoulsen.tes.rest.models.testutils.TestData
import dk.sunepoulsen.tes.rest.models.validation.DefaultValidator
import jakarta.validation.ConstraintViolationException
import spock.lang.Specification

class ServiceMetricsSpec extends Specification {

    private DefaultValidator validator

    void setup() {
        this.validator = new DefaultValidator()
    }

    void "Validate a ServiceMetrics with valid values"() {
        expect:
            this.validator.validate(new ServiceMetrics(
                names: [
                    'application.start.time'
                ]
            ))
    }

    void "Validate an invalid ServiceMetrics where the 'names' property is null"() {
        when:
            this.validator.validate(new ServiceMetrics())

        then:
            ConstraintViolationException exception = thrown(ConstraintViolationException)
            ConstraintViolationAssertions.verifyViolations(exception.constraintViolations, [
                TestData.createError('names', 'must not be null')
            ])
    }

}
