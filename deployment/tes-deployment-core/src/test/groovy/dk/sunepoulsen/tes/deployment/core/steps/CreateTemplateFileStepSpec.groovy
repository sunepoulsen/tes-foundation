package dk.sunepoulsen.tes.deployment.core.steps


import dk.sunepoulsen.tes.data.generators.Generators
import dk.sunepoulsen.tes.deployment.core.data.DeployFileContent
import dk.sunepoulsen.tes.deployment.core.data.DeployUser
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier
import dk.sunepoulsen.tes.flows.exceptions.FlowStepException
import dk.sunepoulsen.tes.templates.VelocityEngineFactory
import spock.lang.Specification

class CreateTemplateFileStepSpec extends Specification {

    private String CONFIGURATION_FILENAME = 'application-ct.yml'

    private CreateTemplateFileStep sut

    void setup() {
        this.sut = new CreateTemplateFileStep('key', VelocityEngineFactory.newInstance(), 'templates/application.yml')
        this.sut.filename = CONFIGURATION_FILENAME
    }

    void "Test timer name"() {
        expect:
            sut.timerName() == 'key'
    }

    void "Test producing configuration file with database settings"() {
        given:
            String password = Generators.passwordGenerator().generate()

            sut.addContextSupplier('databaseHost', new AtomicDataSupplier<String>('postgres'))
            sut.addContextSupplier('databaseName', new AtomicDataSupplier<String>('features'))
            sut.addContextSupplier('applicationUser', new AtomicDataSupplier<DeployUser>(DeployUser.builder()
                .username('app_features')
                .password(password)
                .build()
            ))

        when:
            sut.execute()

        then:
            DeployFileContent fileContent = sut.fileContent.get().orElseThrow()
            fileContent.filename == CONFIGURATION_FILENAME
            fileContent.contentAsString ==
"""# ===================================================================
#                   Spring JPA Configuration
# ===================================================================

spring:
  datasource:
    url: 'jdbc:postgresql://postgres:5432/features'
  username: app_features
  password: ${password}
"""
    }

    void "Test producing configuration file with missing context values"() {
        given:
            String password = Generators.passwordGenerator().generate()

            sut.addContextSupplier('databaseHost', new AtomicDataSupplier<String>())
            sut.addContextSupplier('databaseName', new AtomicDataSupplier<String>('features'))
            sut.addContextSupplier('applicationUser', new AtomicDataSupplier<DeployUser>(DeployUser.builder()
                .username('app_features')
                .password(password)
                .build()
            ))

        when:
            sut.execute()

        then:
            FlowStepException ex = thrown(FlowStepException)
            ex.message == "Value of context supplier 'databaseHost' as not been set"
    }

}
