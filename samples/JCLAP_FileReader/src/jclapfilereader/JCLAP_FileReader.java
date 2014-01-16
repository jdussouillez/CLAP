package jclapfilereader;

import com.args.jclap.options.Option;
import com.args.jclap.options.OptionCreationException;
import com.args.jclap.options.OptionSet;
import com.args.jclap.options.OptionWithValue;
import com.args.jclap.parser.Parser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Simple (and ugly) filereader program using CLAP
 */
public class JCLAP_FileReader {

    public static void main(String[] args) {
        /*
         * Option creation
         */
        Parser parser;
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
