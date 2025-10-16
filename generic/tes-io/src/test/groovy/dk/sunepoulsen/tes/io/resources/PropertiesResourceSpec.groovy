package dk.sunepoulsen.tes.io.resources

import spock.lang.Specification

class PropertiesResourceSpec extends Specification {

    void "Test load properties from null resource"() {
        when:
            new PropertiesResource(null)

        then:
            ResourceException exception = thrown(ResourceException)
            exception.message == 'Unable to load properties from null resource'
    }

    void "Test load property from classpath resource file"() {
        given:
            PropertiesResource sut = new PropertiesResource(this.class.getResourceAsStream('resource.properties'))

        expect:
            sut.getProperty('name') == 'Name value'
    }

}
