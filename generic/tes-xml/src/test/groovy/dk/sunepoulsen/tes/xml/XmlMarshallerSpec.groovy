package dk.sunepoulsen.tes.xml

import dk.sunepoulsen.tes.xml.exceptions.MarshalXmlException
import spock.lang.Specification

import java.nio.charset.StandardCharsets
import java.time.LocalDate

class XmlMarshallerSpec extends Specification {

    void "Marshal Object to XML without charset"() {
        given:
            BookXml book = new BookXml(
                id: 1L,
                name: 'name',
                author: 'author',
                date: LocalDate.of(2023, 5, 17)
            )

        when:
            String result = XmlMarshaller.marshal(book)

        then:
            result ==
                """<?xml version="1.0" encoding="UTF-8" standalone="yes"?><book id="1"><title>name</title><date>2023-05-17</date></book>"""
    }

    void "Marshal Object to XML without charset and formatted output"() {
        given:
            BookXml book = new BookXml(
                id: 1L,
                name: 'name',
                author: 'author',
                date: LocalDate.of(2023, 5, 17)
            )

        when:
            String result = XmlMarshaller.marshal(book, Boolean.TRUE)

        then:
            result ==
"""<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<book id="1">
    <title>name</title>
    <date>2023-05-17</date>
</book>
"""
    }

    void "Marshal Object to XML with charset"() {
        given:
            BookXml book = new BookXml(
                id: 1L,
                name: 'name',
                author: 'author',
                date: LocalDate.of(2023, 5, 17)
            )

        when:
            String result = XmlMarshaller.marshal(book, StandardCharsets.ISO_8859_1)

        then:
            result ==
                """<?xml version="1.0" encoding="ISO-8859-1" standalone="yes"?><book id="1"><title>name</title><date>2023-05-17</date></book>"""
    }

    void "Marshal Object to XML with bad annotations"() {
        given:
            BookXmlBadAnnotations book = new BookXmlBadAnnotations(
                id: 1L,
                name: 'name',
                author: 'author',
                date: LocalDate.of(2023, 5, 17)
            )

        when:
            XmlMarshaller.marshal(book, Boolean.TRUE)

        then:
            thrown(MarshalXmlException)
    }
}
