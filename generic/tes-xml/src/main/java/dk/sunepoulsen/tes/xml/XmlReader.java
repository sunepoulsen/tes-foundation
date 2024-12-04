package dk.sunepoulsen.tes.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to simplify the reading of elements from a XML document with XPaths.
 */
public class XmlReader {
    private final Document xmlDocument;
    private final XPath xPath;

    /**
     * Constructs a <code>XMLReader</code> with a <code>Document</code>.
     * <p>
     * All other methods in the class will operate on this <code>Document</code>.
     * </p>
     *
     * @param xmlDocument The <code>Document</code> to read information from.
     */
    public XmlReader(Document xmlDocument) {
        this.xmlDocument = xmlDocument;
        this.xPath = XPathFactory.newInstance().newXPath();
    }

    /**
     * Reads all XML nodes that matches an XPath expression.
     *
     * @param expression The XPath expression.
     * @return The found nodes or an empty list.
     * @throws XPathExpressionException - If expression cannot be compiled.
     */
    public NodeList readElements(String expression) throws XPathExpressionException {
        return (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
    }

    /**
     * Read one single XML element's content as a string.
     *
     * @param expression The XPath expression that match a single element.
     * @return The content of the single XML Element or <code>null</code> if
     * no element was found or the XML element contains child elements.
     * @throws XPathExpressionException - If expression cannot be compiled.
     */
    public String readElement(String expression) throws XPathExpressionException {
        var node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);

        if (!nodeHasOnlyText(node)) {
            return null;
        }

        return node.getTextContent();
    }

    /**
     * Collects the text content of multiple XML element's into a String list.
     * <p>
     * Elements with child elements or non text content will not be collected in
     * the result.
     * </p>
     *
     * @param expression The XPath expression that match the XML elements to collect from.
     * @return The collected texts from the found XML elements.
     * @throws XPathExpressionException - If expression cannot be compiled.
     */
    public List<String> collectElementsWithText(String expression) throws XPathExpressionException {
        var list = this.readElements(expression);

        if (list.getLength() == 0) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        for (var i = 0; i < list.getLength(); i++) {
            var node = list.item(i);

            if (nodeHasOnlyText(node)) {
                result.add(node.getTextContent());
            }
        }

        return result;
    }

    private static boolean nodeHasOnlyText(Node node) {
        if (node == null) {
            return false;
        }

        if (node.getChildNodes().getLength() != 1) {
            return false;
        }

        return node.getFirstChild().getNodeType() == Node.TEXT_NODE;
    }

}
