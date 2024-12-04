package dk.sunepoulsen.tes.docker.containers

import dk.sunepoulsen.tes.docker.exceptions.DockerImageProviderException
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification
import spock.lang.Unroll

class ClasspathPropertiesDockerImageProviderSpec extends Specification {

    void "Read docker image name of classpath resources with success"() {
        given:
            ClasspathPropertiesDockerImageProvider sut = new ClasspathPropertiesDockerImageProvider('/docker-image-name.properties', 'docker')

        when:
            DockerImageName dockerImageName = sut.dockerImageName()

        then:
            dockerImageName.asCanonicalNameString() == 'ImageName:TagName'
    }

    @Unroll
    void "Read docker image name of classpath resources with error: #_testcase"() {
        given:
            ClasspathPropertiesDockerImageProvider sut = new ClasspathPropertiesDockerImageProvider(_resource, _prefix)

        when:
            sut.dockerImageName()

        then:
            thrown(_exception)

        where:
            _testcase      | _resource                       | _prefix         | _exception
            'Bad resource' | '/bad-resource.properties'      | 'docker'        | DockerImageProviderException.class
            'No image key' | '/docker-image-name.properties' | 'bad-image-key' | DockerImageProviderException.class
            'No tag key'   | '/docker-image-name.properties' | 'no-tag'        | DockerImageProviderException.class
    }

}
