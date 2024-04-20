package dk.sunepoulsen.tes.utils

import dk.sunepoulsen.tes.utils.exceptions.PropertyResourceException
import spock.lang.Specification
import spock.lang.Unroll

class PropertyResourceSpec extends Specification {

    private PropertyResource propertyResource

    void setup() {
        InputStream inputStream = Resources.readResource('/docker-image-name.properties')
        propertyResource = new PropertyResource(inputStream)
    }

    @Unroll
    void "Property resources contains a key: #_testcase"() {
        expect:
            propertyResource.containsKey(_key) == _expected

        where:
            _testcase             | _key                | _expected
            'Key exists'          | 'docker.image.name' | true
            'Key does not exists' | 'missing-key'       | false
    }

    void "Read property with keys"() {
        expect:
            propertyResource.property('docker.image.name') == 'ImageName'
            propertyResource.property('docker.image.name', 'default-value') == 'ImageName'
            propertyResource.property('missing-key.name', 'default-value') == 'default-value'
    }

    void "Read property missing key"() {
        when:
            propertyResource.property('missing-key.name')

        then:
            PropertyResourceException ex = thrown(PropertyResourceException)
            ex.message == "Unable to read property because the key 'missing-key.name' does not exist"
    }

    void "Test static overloaded methods of PropertyResource::readProperty()"() {
        expect:
            PropertyResource.readProperty('/docker-image-name.properties', 'docker.image.name') == 'ImageName'
            PropertyResource.readProperty(PropertyResource.class,'/docker-image-name.properties', 'docker.image.name') == 'ImageName'
            PropertyResource.readProperty('/docker-image-name.properties', 'docker.image.name', 'default-value') == 'ImageName'
            PropertyResource.readProperty(PropertyResource.class,'/docker-image-name.properties', 'docker.image.name', 'default-value') == 'ImageName'
    }
}
