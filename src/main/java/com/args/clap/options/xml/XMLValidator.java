package com.args.clap.options.xml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

/**
 * Validate a xml file which describes the CLAP options using a schema.
 */
public class XMLValidator {

    /**
     * Filename of the schema.
     */
    public static final String xsdFilename = "clap_options.xsd";
    /*
     **********************************************
     * Attributes
     **********************************************
     */
    /**
     * Input XML filename.
     */
    private String xmlFilename;
    /**
     * Error message, null if the xml is validXml.
     */
    private String error;
    /**
     * XML file validXml or not.
     */
    private boolean validXml;

    /*
     **********************************************
     * Constructors
     **********************************************
     */
    /**
     * Creates a new instance of XMLValidator
     *
     * @param xmlFilename The xml filename to validate
     */
    public XMLValidator(String xmlFilename) {
        this.xmlFilename = xmlFilename;
        error = null;
        validXml = false;
    }

    /*
     **********************************************
     * Getters
     **********************************************
     */
    /**
     * Returns the error message, or null.
     *
     * @return Error message
     */
    public String getError() {
        return error;
    }

    /**
     * Returns the validation of the XML file.
     *
     * @return XML is validXml or not
     */
    public boolean isValidXml() {
        return validXml;
    }

    /**
     * Validates the xml file using the CLAP schema file. Changes the value to
     * the "validXml" and "error" attribute
     */
    public void validate() {
        try {
            URL xsdFile = getClass().getResource(xsdFilename);
            Source xmlFile = new StreamSource(new File(xmlFilename));
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            validXml = true;
            error = null;
        } catch (SAXException | IOException | NullPointerException ex) { // Multi-catch only in Java 7
            validXml = false;
            error = ex.getLocalizedMessage();
        }
    }
}
