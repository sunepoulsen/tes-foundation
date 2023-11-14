package dk.sunepoulsen.tes.xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Factory class to create instances of XML Documents.
 */
public class XmlDocumentFactory {

    /**
     * Constructs an XML Document from an input stream.
     *
     * @param inputStream Input stream to the document to parse.
     *
     * @return An XML Document.
     *
     * @throws ParserConfigurationException If the resource could not be parsed as an XML document.
     * @throws IOException – If any IO errors occur.
     * @throws SAXException – If any parse errors occur.
     */
    public Document createDocument(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        var builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        builderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        var builder = builderFactory.newDocumentBuilder();
        return builder.parse(inputStream);
    }

    /**
     * Creates an Xml Document from a classpath resource.
     * <p/>
     * <h4>Note</h4>
     * The resource is loaded by the classloader of <code>XmlDocumentFactory</code> so the full classpath resource path
     * needs to be specified.
     *
     * @param classpathFileName The full path to the classpath resource.
     *
     * @return An XML Document.
     *
     * @throws ParserConfigurationException If the resource could not be parsed as an XML document.
     * @throws IOException – If any IO errors occur.
     * @throws SAXException – If any parse errors occur.
     */
    public Document createDocumentFromClasspath(String classpathFileName) throws ParserConfigurationException, IOException, SAXException {
        return this.createDocumentFromClasspath(this.getClass(), classpathFileName);
    }

    /**
     * Creates a Xml Document from a classpath resource.
     *
     * @param <T> The class type of the class to load the resource from the classpath.
     * @param clazz <code>Class&lt;T&gt;</code> to be used to load the resource.
     * @param classpathFileName Relative or complete path to the classpath resource.
     *
     * @return An XML Document.
     *
     * @throws ParserConfigurationException If the resource could not be parsed as an XML document.
     * @throws IOException – If any IO errors occur.
     * @throws SAXException – If any parse errors occur.
     */
    public <T> Document createDocumentFromClasspath(Class<T> clazz, String classpathFileName) throws ParserConfigurationException, IOException, SAXException {
        return this.createDocument(clazz.getResourceAsStream(classpathFileName));
    }

}
