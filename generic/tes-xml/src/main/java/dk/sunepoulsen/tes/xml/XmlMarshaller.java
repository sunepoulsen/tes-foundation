package dk.sunepoulsen.tes.xml;

import dk.sunepoulsen.tes.xml.exceptions.MarshalXmlException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.PropertyException;

import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Class to marshal Java Objects to XML Strings.
 */
public class XmlMarshaller {

    private XmlMarshaller() {
    }

    /**
     * Marshall an Object to a compact XML string in <code>UTF-8</code>
     *
     * @param value The object to marshall.
     *
     * @return The produced XML string without newlines.
     *
     * @throws MarshalXmlException - in case of an marshall error.
     */
    public static String marshal(Object value) throws MarshalXmlException {
        return marshal(value, StandardCharsets.UTF_8);
    }

    /**
     * Marshall an Object to a compact XML string in <code>UTF-8</code>
     *
     * @param value The object to marshall.
     * @param formatOutput <code>true</code> will format the output with
     *                     new lines and indentation. <code>false</code>
     *                     will produce an output without new lines.
     *
     * @return The produced XML string with respect to the <code>formatOutput</code>
     *         parameter.
     *
     * @throws MarshalXmlException - in case of an marshall error.
     */
    public static String marshal(Object value, Boolean formatOutput) throws MarshalXmlException {
        return marshal(value, StandardCharsets.UTF_8, formatOutput);
    }

    /**
     * Marshall an Object to a XML string with a specific charset.
     *
     * @param value The object to marshall.
     * @param charset The Charset to use.
     *
     * @return The produced XML string without newlines.
     *
     * @throws MarshalXmlException - in case of an marshall error.
     */
    public static String marshal(Object value, Charset charset) throws MarshalXmlException {
        return marshal(value, charset, Boolean.FALSE);
    }

    /**
     * Marshall an Object to a XML string with a specific charset and format.
     *
     * @param value The object to marshall.
     * @param charset The Charset to use.
     * @param formatOutput <code>true</code> will format the output with
     *                     new lines and indentation. <code>false</code>
     *                     will produce an output without new lines.
     *
     * @return The produced XML string with respect to the <code>formatOutput</code>
     *         parameter.
     *
     * @throws MarshalXmlException - in case of an marshall error.
     */
    public static String marshal(Object value, Charset charset, Boolean formatOutput) throws MarshalXmlException {
        var writer = new StringWriter();
        marshal(value, charset, writer, formatOutput);

        return writer.toString();
    }

    /**
     * Marshall an Object to a XML string and stores the result in a
     * <code>Writer</code>
     *
     * @param value The object to marshall.
     * @param charset The Charset to use.
     * @param writer The Writer to hold the result.
     * @param formatOutput <code>true</code> will format the result with
     *                     new lines and indentation. <code>false</code>
     *                     will produce a result without new lines.
     *
     * @throws MarshalXmlException - in case of an marshall error.
     */
    public static void marshal(Object value, Charset charset, Writer writer, Boolean formatOutput) throws MarshalXmlException {
        try {
            var context = JAXBContext.newInstance(value.getClass());

            var mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_ENCODING, charset.name());
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatOutput);
            mar.marshal(value, writer);
        } catch (PropertyException ex) {
            throw new MarshalXmlException("Unknown property for XML marshalling: " + ex.getMessage(), ex);
        } catch (JAXBException ex) {
            throw new MarshalXmlException("Unable to marshall object: " + ex.getMessage(), ex);
        }
    }

}
