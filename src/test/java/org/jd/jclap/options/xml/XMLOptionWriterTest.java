package org.jd.jclap.options.xml;

import org.jd.jclap.options.InvalidOptionLongNameException;
import org.jd.jclap.options.InvalidOptionNamesException;
import org.jd.jclap.options.InvalidOptionShortNameException;
import org.jd.jclap.options.Option;
import org.jd.jclap.options.OptionSet;
import org.jd.jclap.options.OptionWithValue;
import java.io.File;
import java.io.FileNotFoundException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.junit.Assert;
import org.junit.Test;

public class XMLOptionWriterTest {

    /*
     * Test-to-pass
     */
    @Test
    public void XMLMakeOptions_TTP() throws InvalidOptionShortNameException,
                                            InvalidOptionLongNameException,
                                            InvalidOptionNamesException,
                                            ParserConfigurationException,
                                            TransformerConfigurationException,
                                            TransformerException {
        OptionSet options = new OptionSet();
        options.add(new Option('v', "verbose"));
        options.add(new Option((char) 0, "help", "display this help and exit"));
        XMLOptionWriter xmlWriter = new XMLOptionWriter();
        String expectedStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
                + "<options>\n"
                + "  <option>\n"
                + "    <shortname>v</shortname>\n"
                + "    <longname>verbose</longname>\n"
                + "  </option>\n"
                + "  <option>\n"
                + "    <longname>help</longname>\n"
                + "    <helpmsg>display this help and exit</helpmsg>\n"
                + "  </option>\n"
                + "</options>\n";
        Assert.assertEquals(expectedStr, XMLOptionWriter.makeOptions(options));
    }

    @Test
    public void XMLMakeOptionsWithValue_TTP() throws InvalidOptionShortNameException,
                                                     InvalidOptionLongNameException,
                                                     InvalidOptionNamesException,
                                                     ParserConfigurationException,
                                                     TransformerConfigurationException,
                                                     TransformerException {
        OptionSet options = new OptionSet();
        OptionWithValue optSize = new OptionWithValue('s');
        optSize.setValueName("SIZE");
        options.add(optSize);
        String expectedStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
                + "<options>\n"
                + "  <option-value>\n"
                + "    <shortname>s</shortname>\n"
                + "    <value-name>SIZE</value-name>\n"
                + "  </option-value>\n"
                + "</options>\n";
        Assert.assertEquals(expectedStr, XMLOptionWriter.makeOptions(options));
    }

    @Test
    public void XMLWriteOptionsInFile_TTP() throws InvalidOptionShortNameException,
                                                   InvalidOptionLongNameException,
                                                   InvalidOptionNamesException,
                                                   ParserConfigurationException,
                                                   TransformerConfigurationException,
                                                   TransformerException,
                                                   FileNotFoundException {
        OptionSet options = new OptionSet();
        OptionWithValue optSize = new OptionWithValue('s');
        optSize.setValueName("SIZE");
        options.add(optSize);
        String filename = "testXML.xml";
        // Create the file
        XMLOptionWriter.writeXML(options, filename);
        // Delete the file
        File f = new File(filename);
        if (f.exists()) {
            f.delete();
        }
    }
}
