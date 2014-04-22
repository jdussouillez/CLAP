package jclapoptionsexport;

import org.jd.jclap.options.Option;
import org.jd.jclap.options.OptionCreationException;
import org.jd.jclap.options.OptionSet;
import org.jd.jclap.options.OptionWithValue;
import org.jd.jclap.options.xml.XMLOptionWriter;
import java.io.FileNotFoundException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class JCLAP_OptionsExport {

    public static void main(String[] args) {
        /*
         * Creation of the options
         */
        OptionSet options = new OptionSet();
        OptionWithValue optLines;
        Option optNumber, optHelp, optVersion;
        int firstFileIndex;
        try {
            optLines = new OptionWithValue('l', "lines", "output only NBLINES lines");
            optLines.setValueName("NBLINES");
            optNumber = new Option('n', "number", "number all output lines");
            optHelp = new Option((char) 0, "help", "display this help and exit");
            optVersion = new Option((char) 0, "version", "output version information and exit");
        } catch (OptionCreationException e) {
            System.err.println(e.getMessage());
            return;
        }
        options.add(optLines);
        options.add(optNumber);
        options.add(optHelp);
        options.add(optVersion);
        /*
         * Export options
         */
        String filename = "options_backup.xml";
        try {
            XMLOptionWriter.writeXML(options, filename);
            System.out.println("Options exported in \"" + filename + "\"");
        } catch (FileNotFoundException | TransformerException | ParserConfigurationException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
