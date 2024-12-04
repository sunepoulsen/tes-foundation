package dk.sunepoulsen.tes.xml;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XmlReaderTest {

    @Test
    void testReadElementsThatExists() throws Exception {
        XmlDocumentFactory factory = new XmlDocumentFactory();
        Document document = factory.createDocumentFromClasspath("/xml-files/spring-boot-starter-metadata.xml");
        XmlReader reader = new XmlReader(document);

        NodeList list = reader.readElements("/metadata/versioning/versions/version");
        assertNotNull(list);

        int numberOfNodes = list.getLength();
        assertEquals(192, numberOfNodes);
    }

    @Test
    void testReadElementsThatDontExist() throws Exception {
        XmlDocumentFactory factory = new XmlDocumentFactory();
        Document document = factory.createDocumentFromClasspath("/xml-files/spring-boot-starter-metadata.xml");
        XmlReader reader = new XmlReader(document);

        NodeList list = reader.readElements("/metadatas/versioning/versions/version");
        assertNotNull(list);
        assertEquals(0, list.getLength());
    }

    @Test
    void testReadElement_ElementExist() throws Exception {
        XmlDocumentFactory factory = new XmlDocumentFactory();
        Document document = factory.createDocumentFromClasspath("/xml-files/spring-boot-starter-metadata.xml");
        XmlReader reader = new XmlReader(document);

        assertEquals("org.springframework.boot", reader.readElement("/metadata/groupId"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/bad/element",
            "/metadata/versioning/versions",
            "/metadata/versioning/element"
    })
    void testReadElement_NullCases(String element) throws Exception {
        XmlDocumentFactory factory = new XmlDocumentFactory();
        Document document = factory.createDocumentFromClasspath("/xml-files/spring-boot-starter-metadata.xml");
        XmlReader reader = new XmlReader(document);

        assertNull(reader.readElement(element));
    }

    @Test
    void testCollectElementsWithText_Ok() throws Exception {
        XmlDocumentFactory factory = new XmlDocumentFactory();
        Document document = factory.createDocumentFromClasspath("/xml-files/spring-boot-starter-metadata.xml");
        XmlReader reader = new XmlReader(document);

        List<String> list = reader.collectElementsWithText("/metadata/versioning/versions/version");
        assertNotNull(list);
        assertEquals(192, list.size());
        assertEquals("1.0.0.RELEASE", list.get(0));
        assertEquals("1.1.6.RELEASE", list.get(9));
        assertEquals("2.2.5.RELEASE", list.get(99));
        assertEquals("3.1.1", list.get(191));
    }

    @Test
    void testCollectElementsWithText_ElementsWithOtherThanText() throws Exception {
        XmlDocumentFactory factory = new XmlDocumentFactory();
        Document document = factory.createDocumentFromClasspath("/xml-files/spring-boot-starter-metadata.xml");
        XmlReader reader = new XmlReader(document);

        assertEquals(Collections.emptyList(), reader.collectElementsWithText("/metadata/versioning/versions"));
    }

    @Test
    void testCollectElementsWithText_NoElementsFound() throws Exception {
        XmlDocumentFactory factory = new XmlDocumentFactory();
        Document document = factory.createDocumentFromClasspath("/xml-files/spring-boot-starter-metadata.xml");
        XmlReader reader = new XmlReader(document);

        assertEquals(Collections.emptyList(), reader.collectElementsWithText("/metadata/versioning/bad/element"));
    }
}
