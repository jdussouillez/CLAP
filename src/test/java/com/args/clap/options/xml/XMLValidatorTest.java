package com.args.clap.options.xml;

import java.net.URISyntaxException;
import org.junit.Assert;
import org.junit.Test;

public class XMLValidatorTest {
    
    private String xmlFile;
    
    public XMLValidatorTest() throws URISyntaxException {
        xmlFile = getClass().getResource("xmlSample1_OK.xml").toString().replace("file:", "");
    }
    
    /*
     * Test-to-pass
     */
    @Test
    public void XML_ValidOptionsFormat_TTP() throws URISyntaxException {
        XMLValidator validator = new XMLValidator(xmlFile);
        validator.validate();
        Assert.assertTrue(validator.isValidXml());
    }
    
    /*
     * Test-to-fail
     */
    @Test
    public void XMLFileNotFound_TTF() {
        XMLValidator validator = new XMLValidator("ghostFile.xml");
        validator.validate();
        Assert.assertFalse(validator.isValidXml());
    }

    @Test
    public void XML_BadFormat_TTF() {
        XMLValidator validator = new XMLValidator(getClass().getResource("xmlSample2_BadFormat.xml").toString().replace("file:", ""));
        validator.validate();
        Assert.assertFalse(validator.isValidXml());
        Assert.assertTrue(validator.getError().contains("Invalid content was found starting with element 'helpmsg'. No child element is expected at this point."));
    }
    
    @Test
    public void XML_UnexpectedAttribute_TTF() {
        XMLValidator validator = new XMLValidator(getClass().getResource("xmlSample3_UnexpectedAttrib.xml").toString().replace("file:", ""));
        validator.validate();
        Assert.assertFalse(validator.isValidXml());
    }
}
