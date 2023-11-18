package dk.sunepoulsen.tes.xml;

import dk.sunepoulsen.tes.xml.exceptions.UnmarshalXmlException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import java.io.Reader;
import java.io.StringReader;

/**
 * Class to unmarshal XML Strings to Java Objects.
 */
public class XmlUnmarshaller {

    private XmlUnmarshaller() {
    }

    /**
     * Unmarshall a XML string to an Java Object
     *
     * @param <T> The return type of the unmarshalled result.
     * @param xml The object to unmarshall.
     * @param clazz The Java type to unmarshall the XML to.
     *
     * @return The unmarshalled result.
     *
     * @throws UnmarshalXmlException - in case of an unmarshall error.
     */
    public static <T> T unmarshal(String xml, Class<T> clazz) throws UnmarshalXmlException {
        return unmarshal(new StringReader(xml), clazz);
    }

    /**
     * Unmarshall a <code>Reader</code> to an Java Object
     *
     * @param <T> The return type of the unmarshalled result.
     * @param xml The object to unmarshall.
     * @param clazz The Java type to unmarshall the XML to.
     *
     * @return The unmarshalled result.
     *
     * @throws UnmarshalXmlException - in case of an unmarshall error.
     */
    public static <T> T unmarshal(Reader xml, Class<T> clazz) throws UnmarshalXmlException {
        try {
            var context = JAXBContext.newInstance(clazz);
            return (T)context.createUnmarshaller().unmarshal(xml);
        } catch (JAXBException ex) {
            throw new UnmarshalXmlException("Unable to unmarshal XML: " + ex.getMessage(), ex);
        }

    }

}
