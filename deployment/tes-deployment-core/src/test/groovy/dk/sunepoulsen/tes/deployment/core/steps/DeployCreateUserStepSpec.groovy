package dk.sunepoulsen.tes.deployment.core.steps

import dk.sunepoulsen.tes.data.generators.DataGenerator
import dk.sunepoulsen.tes.data.generators.Generators
import dk.sunepoulsen.tes.deployment.core.data.DeployUser
import dk.sunepoulsen.tes.flows.FlowStepResult
import spock.lang.Specification

class DeployCreateUserStepSpec extends Specification {

    void "Test timer name"() {
        given:
            DataGenerator<String> username = Generators.fixedGenerator("admin")
            DataGenerator<String> password = Generators.fixedGenerator(Generators.passwordGenerator())

            DeployCreateUserStep sut = new DeployCreateUserStep('db.admin.user', username, password)

        expect:
            sut.timerName() == 'db.admin.user'
    }

    void "Tests step with new created user successfully"() {
        given:
            DataGenerator<String> username = Generators.fixedGenerator("admin")
            DataGenerator<String> password = Generators.fixedGenerator(Generators.passwordGenerator())

            DeployCreateUserStep sut = new DeployCreateUserStep('db.admin.user', username, password)

        when:
            sut.execute() == FlowStepResult.OK

        then:
            Optional<DeployUser> newUser = sut.createdUser.get()

            !newUser.empty
            newUser.get().username == username.generate()
            newUser.get().password == password.generate()
    }

    void "Tests step with new created user failing because username is null"() {
        given:
            DataGenerator<String> password = Generators.fixedGenerator(Generators.passwordGenerator())
            DeployCreateUserStep sut = new DeployCreateUserStep('db.admin.user', null, password)

        when:
            sut.execute()

        then:
            IllegalStateException ex = thrown(IllegalStateException)
            ex.message == 'username may not be null'
    }

    void "Tests step with new created user failing because password is null"() {
        given:
            DataGenerator<String> username = Generators.fixedGenerator('admin')
            DeployCreateUserStep sut = new DeployCreateUserStep('db.admin.user', username, null)

        when:
            sut.execute()

        then:
            IllegalStateException ex = thrown(IllegalStateException)
            ex.message == 'password may not be null'
    }

}
