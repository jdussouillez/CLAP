package jclapfilereader;

import com.args.jclap.options.Option;
import com.args.jclap.options.OptionSet;
import com.args.jclap.options.OptionWithValue;
import com.args.jclap.options.xml.XMLOptionReader;
import com.args.jclap.parser.Parser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Simple (and ugly) filereader program using JCLAP
 */
public class JCLAP_FileReader_XMLOptions {

    public static final String xmlOptionsFile = "FileReaderOptions.xml";

    public static void main(String[] args) {
        OptionSet options;
        Parser parser;
        OptionWithValue optLines;
        Option optNumber, optHelp, optVersion;
        int firstFileIndex;
        /*
         * Load options from XML
         */
        try {
            options = XMLOptionReader.parse(xmlOptionsFile);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.err.println(ex.getMessage());
            return;
        }
        optLines = (OptionWithValue) options.getByShortName('l');
        optNumber = options.getByShortName('n');
        optHelp = options.getByLongName("help");
        optVersion = options.getByLongName("version");
        /*
         * Args parsing
         */
        parser = new Parser("CLAPTest_FileReader");
        firstFileIndex = parser.parse(options, args);
        if (firstFileIndex == -1) {
            // Parsing error
            System.err.println(parser.getErrorMsg());
            return;
        }
        if (args.length == 0) {
            // No argument, display the help
            optHelp.setIsSet(true);
        }
        // Version
        if (optVersion.isSet()) {
            System.out.println("CLAPTest_FileReader 1.0\n....");
            return;
        }
        // Help
        if (optHelp.isSet()) {
            System.out.println("CLAPTest_FileReader [OPTION] FILE ...");
            System.out.println(options.getHelpMessage());
            return;
        }
        int lines = -1; // Display all lines
        if (optLines.isSet()) {
            try {
                lines = Integer.parseInt(optLines.getValue());
            } catch (NumberFormatException e) {
                lines = -1;
            }
            if (lines < 1) {
                System.err.println("Invalid number of lines (has to be greater than 0)");
                return;
            }
        }
        try {
            String files[] = Arrays.copyOfRange(args, firstFileIndex, args.length);
            if (files.length == 0) {
                System.err.println("Missing file(s)");
                return;
            }
            write(files, optNumber.isSet(), lines);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void write(String[] files, boolean number, int lines) throws FileNotFoundException, IOException {
        BufferedReader reader;
        String line;
        int num = 1;
        for (int i = 0; i < files.length; i++) {
            reader = new BufferedReader(new FileReader(files[i]));
            while ((line = reader.readLine()) != null && (lines == -1 || num < lines)) {
                System.out.println(((number) ? num + " " : "") + line);
                num++;
            }
        }
    }
}
