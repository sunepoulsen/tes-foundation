package dk.sunepoulsen.tes.utils

import dk.sunepoulsen.tes.utils.exceptions.ResourceException
import spock.lang.Specification

class ResourcesSpec extends Specification {

    void "Read resource from classpath: Success"() {
        expect:
            Resources.readResource('/docker-image-name.properties') != null
            Resources.readResource(Resources.class, '/docker-image-name.properties') != null
    }

    void "Read resource from classpath: Not found"() {
        when:
            Resources.readResource('/not-found.properties')

        then:
            ResourceException ex = thrown(ResourceException)
            ex.message == "Unable to load classpath resource '/not-found.properties'"
    }

    void "Read resource, using a class, from classpath: Not found"() {
        when:
            Resources.readResource(Resources.class, '/not-found.properties')

        then:
            ResourceException ex = thrown(ResourceException)
            ex.message == "Unable to load classpath resource '/not-found.properties'"
    }

}
