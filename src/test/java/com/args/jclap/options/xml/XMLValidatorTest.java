package com.args.jclap.options.xml;

import java.net.URISyntaxException;
import org.junit.Assert;
import org.junit.Test;

public class XMLValidatorTest {

    /*
     * Test-to-pass
     */
    @Test
    public void XML_ValidOptionsFormat_TTP() throws URISyntaxException {
        XMLValidator validator = new XMLValidator(getClass().getResource("xmlSample1_OK.xml").toString().replace("file:", ""));
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
        Assert.assertTrue(validator.getError().contains("(No such file or directory)"));
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
        Assert.assertTrue(validator.getError().contains("Attribute 'id' is not allowed to appear in element 'option'"));
    }

    @Test
    public void XML_EmptyShortName_TTF() {
        XMLValidator validator = new XMLValidator(getClass().getResource("xmlSample4_EmptyShortName.xml").toString().replace("file:", ""));
        validator.validate();
        Assert.assertFalse(validator.isValidXml());
        Assert.assertTrue(validator.getError().contains("'0' is not facet-valid with respect to minLength '1' for type 'type_shortname'"));
    }
}
