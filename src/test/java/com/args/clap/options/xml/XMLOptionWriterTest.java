package com.args.clap.options.xml;

import com.args.clap.options.InvalidOptionLongNameException;
import com.args.clap.options.InvalidOptionNamesException;
import com.args.clap.options.InvalidOptionShortNameException;
import com.args.clap.options.Option;
import com.args.clap.options.OptionSet;
import com.args.clap.options.OptionWithValue;
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
        String expectedStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
                + "<options>"
                + "<option>"
                + "<shortname>v</shortname>"
                + "<longname>verbose</longname>"
                + "</option>"
                + "<option>"
                + "<longname>help</longname>"
                + "<helpmsg>display this help and exit</helpmsg>"
                + "</option>"
                + "</options>";
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
        String expectedStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
                + "<options>"
                + "<option-value>"
                + "<shortname>s</shortname>"
                + "<value-name>SIZE</value-name>"
                + "</option-value>"
                + "</options>";
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
