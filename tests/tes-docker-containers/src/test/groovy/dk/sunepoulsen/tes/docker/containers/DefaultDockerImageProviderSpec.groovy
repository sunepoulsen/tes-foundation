package dk.sunepoulsen.tes.docker.containers

import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

class DefaultDockerImageProviderSpec extends Specification {

    void "Provide docker image name with success"() {
        given:
            DefaultDockerImageProvider sut = new DefaultDockerImageProvider('name', 'tag')

        when:
            DockerImageName result = sut.dockerImageName()

        then:
            result.toString() == 'name:tag'
    }

}
