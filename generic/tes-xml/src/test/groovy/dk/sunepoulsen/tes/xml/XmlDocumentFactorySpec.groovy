package dk.sunepoulsen.tes.xml

import spock.lang.Specification

class XmlDocumentFactorySpec extends Specification {

    void "Create Document from InputStream"() {
        given: 'Factory class'
            XmlDocumentFactory factory = new XmlDocumentFactory()

        expect:
            factory.createDocument(this.class.getResourceAsStream('/xml-files/spring-boot-starter-metadata.xml')) != null
    }

    void "Create Document from classpath"() {
        given: 'Factory class'
            XmlDocumentFactory factory = new XmlDocumentFactory()

        expect:
            factory.createDocumentFromClasspath('/xml-files/spring-boot-starter-metadata.xml') != null
    }

}
