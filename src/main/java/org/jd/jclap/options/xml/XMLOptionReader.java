package org.jd.jclap.options.xml;

import org.jd.jclap.options.InvalidOptionLongNameException;
import org.jd.jclap.options.InvalidOptionNamesException;
import org.jd.jclap.options.InvalidOptionShortNameException;
import org.jd.jclap.options.Option;
import org.jd.jclap.options.OptionSet;
import org.jd.jclap.options.OptionWithValue;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Read a XML file to create an option set. If the XML has a valid format,
 * an exception is thrown. If the format is valid but an option is not, an
 * OptionCreationException is thrown. If there is several options with the same
 * short/long name, the first one is kept and the other is not added in the
 * options.
 */
public class XMLOptionReader extends DefaultHandler {

    /*
     **********************************************
     * Attributes
     **********************************************
     */
    /**
     * Set of options
     */
    private OptionSet options;
    /**
     * Current option's short name.
     */
    private char shortName;
    /**
     * Current option's long name.
     */
    private String longName;
    /**
     * Current option's help message.
     */
    private String helpMsg;
    /**
     * Current option's value name (OptionWithValue only).
     */
    private String valueName;
    /**
     * Parser is in a "shortname" tag ?.
     */
    private boolean inShortName;
    /**
     * Parser is in a "longname" tag ?.
     */
    private boolean inLongName;
    /**
     * Parser is in a "helpmsg" tag ?.
     */
    private boolean inHelpMsg;
    /**
     * Parser is in a "value-name" tag ?.
     */
    private boolean inValueName;

    /*
     **********************************************
     * Constructor
     **********************************************
     */
    private XMLOptionReader() {
        options = new OptionSet();
        resetOption();
    }

    /*
     **********************************************
     * Getters
     **********************************************
     */
    /**
     * Returns the options created by the parser
     *
     * @return An non-null option set
     */
    public OptionSet getOptions() {
        return options;
    }

    /*
     **********************************************
     * SAX methods
     **********************************************
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "shortname":
                inShortName = true;
                break;
            case "longname":
                inLongName = true;
                break;
            case "helpmsg":
                inHelpMsg = true;
                break;
            case "value-name":
                inValueName = true;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inShortName) {
            shortName = ch[start];
        }
        else if (inLongName) {
            longName = new String(ch, start, length);
        }
        else if (inHelpMsg) {
            helpMsg = new String(ch, start, length);
        }
        else if (inValueName) {
            valueName = new String(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "option":
                try {
                    // End of an option
                    options.add(new Option(shortName, longName, helpMsg));
                    resetOption();
                } catch (InvalidOptionShortNameException | InvalidOptionLongNameException | InvalidOptionNamesException ex) {
                    /*
                     * Throws a SAXException wrapping the "real" exception.
                     * The "real" exception has to be thrown this way because it
                     * does not inherit from SAXException (and you can not throw
                     * others exception because the method is Override)
                     */
                    throw new SAXException(ex);
                }
                break;
            case "option-value":
                try {
                    OptionWithValue opt = new OptionWithValue(shortName, longName, helpMsg);
                    opt.setValueName(valueName); // If "valueName" is null, the default value is set.
                    options.add(opt);
                    resetOption();
                } catch (InvalidOptionShortNameException | InvalidOptionLongNameException | InvalidOptionNamesException ex) {
                    throw new SAXException(ex);
                }
                break;
            case "shortname":
                inShortName = false;
                break;
            case "longname":
                inLongName = false;
                break;
            case "helpmsg":
                inHelpMsg = false;
                break;
            case "value-name":
                inValueName = false;
                break;
        }
    }

    /*
     **********************************************
     * Other methods
     **********************************************
     */
    /**
     * Reset the option. To use to reset all values set by previous parsing
     */
    private void resetOption() {
        shortName = (char) 0;
        longName = null;
        helpMsg = null;
        valueName = null;
    }

    /**
     * Returns the options created from the XML file.
     *
     * @param xmlFilePath Path of the xmlFile
     * @return The options created
     *
     * @throws ParserConfigurationException Parser creation failed
     * @throws SAXException                 Invalid XML format or option creation error
     * @throws IOException                  xmlFile not found
     */
    public static OptionSet parse(String xmlFilePath) throws ParserConfigurationException,
                                                             SAXException,
                                                             IOException {
        // Validation
        XMLValidator xmlValidator = new XMLValidator(xmlFilePath);
        xmlValidator.validate();
        if (!xmlValidator.isValidXml()) {
            throw new SAXException(xmlValidator.getError());
        }
        // Parsing
        XMLOptionReader xmlReader = new XMLOptionReader();
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        parser.parse(new File(xmlFilePath), xmlReader);
        return xmlReader.getOptions();
    }
}
