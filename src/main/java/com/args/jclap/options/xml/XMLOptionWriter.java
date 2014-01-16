package com.args.jclap.options.xml;

import com.args.jclap.options.Option;
import com.args.jclap.options.OptionSet;
import com.args.jclap.options.OptionWithValue;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Creates a XML options file using an OptionSet object.
 */
public class XMLOptionWriter {

    /**
     * Creates a XML file describing the options given in parameter.
     *
     * @param options The options set
     * @param xmlFilename Path of the XML file
     * @throws FileNotFoundException XML filepath does not exists
     * @throws ParserConfigurationException XML parser error
     * @throws TransformerConfigurationException XML parser error
     * @throws TransformerException XML parser error
     */
    public static void writeXML(OptionSet options, String xmlFilename) throws FileNotFoundException,
                                                                              ParserConfigurationException,
                                                                              TransformerConfigurationException,
                                                                              TransformerException {
        try (PrintStream file = new PrintStream(new FileOutputStream(xmlFilename))) {
            file.print(makeOptions(options));
        }
    }

    /**
     * Returns a XML string describing the options given in parameter.
     *
     * @param options The options set
     * @return The XML string
     * @throws ParserConfigurationException XML parser error
     * @throws TransformerConfigurationException XML parser error
     * @throws TransformerException XML parser error
     */
    public static String makeOptions(OptionSet options) throws ParserConfigurationException,
                                                               TransformerConfigurationException,
                                                               TransformerException {
        String xmlStr;
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .getDOMImplementation().createDocument(null, "options", null);
        Element root = doc.getDocumentElement();
        // Create options
        Iterator<Option> it = options.iterator();
        while (it.hasNext()) {
            root.appendChild(makeOption(it.next(), doc));
        }
        // Write result in a string
        Transformer trans = TransformerFactory.newInstance().newTransformer();
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        StringWriter writer = new StringWriter();
        trans.transform(new DOMSource(doc), new StreamResult(writer));
        xmlStr = writer.getBuffer().toString();
        return xmlStr;
    }

    /**
     * Returns a XML <option> tag built with the option using a XML document.
     *
     * @param opt Option used to create the string
     * @param doc XML document
     * @return XML element
     */
    private static Element makeOption(Option opt, Document doc) {
        boolean optionWithValue = (opt instanceof OptionWithValue);
        Element e = doc.createElement(optionWithValue ? "option-value" : "option");
        if (opt.getShortName() != (char) 0) {
            e.appendChild(makeLeaf(String.valueOf(opt.getShortName()), "shortname", doc));
        }
        if (opt.getLongName() != null) {
            e.appendChild(makeLeaf(opt.getLongName(), "longname", doc));
        }
        if (opt.getHelpMessage() != null) {
            e.appendChild(makeLeaf(opt.getHelpMessage(), "helpmsg", doc));
        }
        if (optionWithValue) {
            String valueName = ((OptionWithValue) opt).getValueName();
            if (valueName != null && !valueName.equals(OptionWithValue.DEFAULT_VALUE_NAME)) {
                e.appendChild(makeLeaf(valueName, "value-name", doc));
            }
        }
        return e;
    }

    /**
     * Returns a XML tag element.
     *
     * @param content Content of the tag
     * @param elementName Name of the tag
     * @param doc XML document
     * @return XML element
     */
    private static Element makeLeaf(String content, String elementName, Document doc) {
        Element e = doc.createElement(elementName);
        e.appendChild(doc.createTextNode(content));
        return e;
    }
}
