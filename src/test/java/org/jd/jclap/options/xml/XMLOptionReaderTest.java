package org.jd.jclap.options.xml;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.xml.parsers.ParserConfigurationException;
import org.jd.jclap.options.Option;
import org.jd.jclap.options.OptionSet;
import org.jd.jclap.options.OptionWithValue;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

public class XMLOptionReaderTest {

    /*
     * Test-to-pass
     */
    @Test
    public void XML_ValidOptions_TTP() throws URISyntaxException, ParserConfigurationException, SAXException, IOException {
        OptionSet options = XMLOptionReader.parse(getClass().getResource("xmlSample1_OK.xml").toString().replace("file:", ""));
        OptionWithValue linesOpt;
        Option numberOpt, helpOpt, versionOpt;
        linesOpt = (OptionWithValue) options.getByShortName('l');
        numberOpt = options.getByShortName('n');
        helpOpt = options.getByLongName("help");
        versionOpt = options.getByLongName("version");
        Assert.assertTrue("Options are missing", linesOpt != null && numberOpt != null && helpOpt != null && versionOpt != null);
        Assert.assertEquals("Invalid \"lines\" option", linesOpt.toString(), "-l NBLINES, --lines=NBLINES\n\toutput only NBLINES lines");
        Assert.assertEquals("Invalid \"number\" option", numberOpt.toString(), "-n, --number\n\tnumber all output lines");
        Assert.assertEquals("Invalid \"help\" option", helpOpt.toString(), "--help\n\tdisplay this help and exit");
        Assert.assertEquals("Invalid \"version\" option", versionOpt.toString(), "--version\n\toutput version information and exit");
    }

    /*
     * Test-to-fail
     */
    @Test
    public void XML_NoNameOption_TTF() throws URISyntaxException, ParserConfigurationException, IOException {
        try {
            OptionSet options = XMLOptionReader.parse(getClass().getResource("xmlSample5_NoNameOption.xml").toString().replace("file:", ""));
        } catch (SAXException ex) {
            Assert.assertEquals("Wrong error", ex.getMessage(), "Option creation : an option has to contain at least a short or a long name");
        }
    }

    @Test
    public void XML_BadXMLFormat_TTF() throws URISyntaxException, ParserConfigurationException, IOException {
        try {
            OptionSet options = XMLOptionReader.parse(getClass().getResource("xmlSample2_BadFormat.xml").toString().replace("file:", ""));
        } catch (SAXException ex) {
            Assert.assertTrue("Wrong error", ex.getMessage().contains("Invalid content was found starting with element 'helpmsg'"));
        }
    }

    @Test
    public void XML_OptionWithValueNoNames() throws URISyntaxException, ParserConfigurationException, IOException {
        try {
            OptionSet options = XMLOptionReader.parse(getClass().getResource("xmlSample6_OptionWithValueNoNames.xml").toString().replace("file:", ""));
        } catch (SAXException ex) {
            Assert.assertTrue("Wrong error", ex.getMessage().contains("Option creation : an option has to contain at least a short or a long name"));
        }
    }
}
