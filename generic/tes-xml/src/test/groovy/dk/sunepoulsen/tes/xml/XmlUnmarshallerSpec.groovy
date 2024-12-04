package dk.sunepoulsen.tes.xml

import dk.sunepoulsen.tes.xml.exceptions.UnmarshalXmlException
import spock.lang.Specification

import java.time.LocalDate

class XmlUnmarshallerSpec extends Specification {

    void "Unmarshal Object from XML"() {
        given:
            String xml = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<book id="1">
    <title>name</title>
    <date>2023-05-17</date>
</book>
"""

        expect:
            XmlUnmarshaller.unmarshal(xml, BookXml.class) == new BookXml(
                id: 1L,
                name: 'name',
                author: null,
                date: LocalDate.of(2023, 5, 17)
            )
    }

    void "Unmarshal wrong object type from valid XML"() {
        given:
            String xml = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<book id="1">
    <title>name</title>
    <date>2023-05-17</date>
</book>
"""

        when:
            XmlUnmarshaller.unmarshal(xml, LocalDateAdapter.class)

        then:
            thrown(UnmarshalXmlException)
    }

    void "Unmarshal invalid XML"() {
        given:
            String xml = "bad xml"

        when:
            XmlUnmarshaller.unmarshal(xml, BookXml.class)

        then:
            thrown(UnmarshalXmlException)
    }

}
