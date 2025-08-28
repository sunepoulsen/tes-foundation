package dk.sunepoulsen.tes.validation.tests

import dk.sunepoulsen.tes.rest.models.monitoring.ServiceInfo
import dk.sunepoulsen.tes.rest.models.monitoring.ServiceInfoApp
import dk.sunepoulsen.tes.rest.models.monitoring.ServiceInfoService
import dk.sunepoulsen.tes.data.generators.Generators
import dk.sunepoulsen.tes.data.generators.NumberGenerators
import dk.sunepoulsen.tes.data.generators.DataGenerator
import jakarta.validation.Validation
import jakarta.validation.Validator
import spock.lang.Specification

class ConstraintViolationAssertionsSpec extends Specification {

    private Validator validator
    private DataGenerator<String> textGenerator

    void setup() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator()
        this.textGenerator = Generators.textGenerator(NumberGenerators.integerGenerator(1, 30))
    }

    void "Tests assertions with no violations"() {
        given:
            ServiceInfo model = new ServiceInfo(
                app: new ServiceInfoApp(
                    name: textGenerator.generate(),
                    version: textGenerator.generate(),
                    service: new ServiceInfoService(
                        name: textGenerator.generate()
                    )
                )
            )

        when:
            ConstraintViolationAssertions.verifyViolations(validator.validate(model), [])

        then:
            noExceptionThrown()
    }

    void "Tests assertions with correct violations"() {
        given:
            ServiceInfo model = new ServiceInfo(
                app: new ServiceInfoApp(
                    name: textGenerator.generate(),
                    version: '',
                    service: new ServiceInfoService(
                        name: null
                    )
                )
            )

        when:
            ConstraintViolationAssertions.verifyViolations(validator.validate(model), [
                new ExpectedConstraintViolation('app.service.name', 'must not be null'),
                new ExpectedConstraintViolation('app.service.name', 'must not be blank'),
                new ExpectedConstraintViolation('app.version', 'must not be blank')
            ])

        then:
            noExceptionThrown()
    }

    void "Tests assertions with different order of correct violations"() {
        given:
            ServiceInfo model = new ServiceInfo(
                app: new ServiceInfoApp(
                    name: textGenerator.generate(),
                    version: '',
                    service: new ServiceInfoService(
                        name: null
                    )
                )
            )

        when:
            ConstraintViolationAssertions.verifyViolations(validator.validate(model), [
                new ExpectedConstraintViolation('app.service.name', 'must not be blank'),
                new ExpectedConstraintViolation('app.version', 'must not be blank'),
                new ExpectedConstraintViolation('app.service.name', 'must not be null')
            ])

        then:
            noExceptionThrown()
    }

    void "Tests assertions with wrong number of violations"() {
        given:
            ServiceInfo model = new ServiceInfo(
                app: new ServiceInfoApp(
                    name: textGenerator.generate(),
                    version: textGenerator.generate(),
                    service: new ServiceInfoService(
                        name: null
                    )
                )
            )

        when:
            ConstraintViolationAssertions.verifyViolations(validator.validate(model), [])

        then:
            thrown(AssertionError)
    }

    void "Tests assertions with wrong property path of violation"() {
        given:
            ServiceInfo model = new ServiceInfo(
                app: new ServiceInfoApp(
                    name: textGenerator.generate(),
                    version: '',
                    service: new ServiceInfoService(
                        name: null
                    )
                )
            )

        when:
            ConstraintViolationAssertions.verifyViolations(validator.validate(model), [
                new ExpectedConstraintViolation('app.service.bad-property', 'must not be blank'),
                new ExpectedConstraintViolation('app.version', 'must not be blank'),
                new ExpectedConstraintViolation('app.service.name', 'must not be null')
            ])

        then:
            thrown(AssertionError)
    }

    void "Tests assertions with wrong message of violation"() {
        given:
            ServiceInfo model = new ServiceInfo(
                app: new ServiceInfoApp(
                    name: textGenerator.generate(),
                    version: '',
                    service: new ServiceInfoService(
                        name: null
                    )
                )
            )

        when:
            ConstraintViolationAssertions.verifyViolations(validator.validate(model), [
                new ExpectedConstraintViolation('app.service.name', 'wrong message'),
                new ExpectedConstraintViolation('app.version', 'must not be blank'),
                new ExpectedConstraintViolation('app.service.name', 'must not be null')
            ])

        then:
            thrown(AssertionError)
    }

}
