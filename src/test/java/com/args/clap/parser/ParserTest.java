package com.args.clap.parser;

import com.args.clap.options.InvalidOptionLongNameException;
import com.args.clap.options.InvalidOptionNamesException;
import com.args.clap.options.InvalidOptionShortNameException;
import com.args.clap.options.Option;
import com.args.clap.options.OptionSet;
import com.args.clap.options.OptionWithValue;
import org.junit.Assert;
import org.junit.Test;

public class ParserTest {
    
    public static final String appName = "testApp";
    /*
     **********************************************
     * Attributes (options)
     **********************************************
     */
    Parser parser;
    private OptionSet options;
    private OptionWithValue optSize;
    private Option optVerbose;
    private Option optVersion;
    private Option optHelp;
    
    /*
     **********************************************
     * Constructor
     **********************************************
     */
    public ParserTest() throws InvalidOptionShortNameException,
                               InvalidOptionLongNameException,
                               InvalidOptionNamesException {
        parser = new Parser(appName);
        options = new OptionSet();
        optSize = new OptionWithValue('s', "size");
        optVerbose = new Option('v', "verbose");
        optVersion = new Option("version");
        optHelp = new Option("help");
        options.add(optSize);
        options.add(optVerbose);
        options.add(optVersion);
        options.add(optHelp);
    }
    
    
    /*
     **********************************************
     * Short name options
     **********************************************
     */
    
    /*
     * Test-to-pass
     */
    @Test
    public void ParserOnlyOneShortOptionValid_TTP() {
        resetOptions();
        parser.reset();
        int firstNonOptionArgIndex = parser.parse(options, new String[] {"-s", "10"});
        Assert.assertEquals("Invalid first non-option argument index", 2, firstNonOptionArgIndex);
        Assert.assertTrue("Option not set (but should have been)", optSize.isSet());
    }
    
    @Test
    public void ParserSeveralShortOptionsValid_TTP() {
        resetOptions();
        parser.reset();
        int firstNonOptionArgIndex = parser.parse(options, new String[] {"-vs", "10"});
        Assert.assertEquals("Invalid first non-option argument index", 2, firstNonOptionArgIndex);
        Assert.assertTrue("Option not set (but should have been)", optSize.isSet());
        Assert.assertTrue("Option not set (but should have been)", optVerbose.isSet());
    }
    
    @Test
    public void ParserInvalidShortOption_TTP() {
        resetOptions();
        parser.reset();
        int firstNonOptionArgIndex = parser.parse(options, new String[] {"-s", "10", "-b"});
        Assert.assertEquals("Invalid first non-option argument index", -1, firstNonOptionArgIndex);
        String expectedErr = appName + ": invalid option -- 'b'\nTry '" + appName + " --help' for more information.";
        Assert.assertEquals("Wrong error msg", expectedErr, parser.getErrorMsg());
    }
    
    @Test
    public void ParserInvalidShortSeveralOption_TTP() {
        resetOptions();
        parser.reset();
        int firstNonOptionArgIndex = parser.parse(options, new String[] {"-bs", "10"});
        Assert.assertEquals("Invalid first non-option argument index", -1, firstNonOptionArgIndex);
        String expectedErr = appName + ": invalid option -- 'b'\nTry '" + appName + " --help' for more information.";
        Assert.assertEquals("Wrong error msg", expectedErr, parser.getErrorMsg());
    }
    
    @Test
    public void ParserValidShortOptionMissingArg_TTP() {
        resetOptions();
        parser.reset();
        int firstNonOptionArgIndex = parser.parse(options, new String[] {"-s"});
        Assert.assertEquals("Invalid first non-option argument index", -1, firstNonOptionArgIndex);
        String expectedErr = appName + ": option 's' requires an argument\nTry '" + appName + " --help' for more information.";
        Assert.assertEquals("Wrong error msg", expectedErr, parser.getErrorMsg());
    }
    
    @Test
    public void ParserValidSeveralShortOptionsMissingArg_TTP() {
        resetOptions();
        parser.reset();
        int firstNonOptionArgIndex = parser.parse(options, new String[] {"-sv"});
        Assert.assertEquals("Invalid first non-option argument index", -1, firstNonOptionArgIndex);
        String expectedErr = appName + ": option 's' requires an argument\nTry '" + appName + " --help' for more information.";
        Assert.assertEquals("Wrong error msg", expectedErr, parser.getErrorMsg());
    }
    
    @Test
    public void ParserNoOptionNoArg_TTP() {
        resetOptions();
        parser.reset();
        int firstNonOptionArgIndex = parser.parse(options, new String[] {"-"});
        Assert.assertEquals("Invalid first non-option argument index", -1, firstNonOptionArgIndex);
        String expectedErr = appName + ": invalid option -- '-'\nTry '" + appName + " --help' for more information.";
        Assert.assertEquals("Wrong error msg", expectedErr, parser.getErrorMsg());
    }
    
    
    /*
     **********************************************
     * Long name options
     **********************************************
     */
    @Test
    public void ParserValidLongOptionNoArg_TTP() {
        resetOptions();
        parser.reset();
        int firstNonOptionArgIndex = parser.parse(options, new String[] {"--version"});
        Assert.assertEquals("Invalid first non-option argument index", 1, firstNonOptionArgIndex);
        Assert.assertTrue("Option not set (but should have been)", optVersion.isSet());
    }
    
    @Test
    public void ParserValidLongOptionWithArg_TTP() {
        resetOptions();
        parser.reset();
        String val = "10";
        int firstNonOptionArgIndex = parser.parse(options, new String[] {"--size=" + val});
        Assert.assertEquals("Invalid first non-option argument index", 1, firstNonOptionArgIndex);
        Assert.assertTrue("Option not set (but should have been)", optSize.isSet());
        Assert.assertEquals("Wrong option's value", val, optSize.getValue());
    }
    
    @Test
    public void ParserInvalidLongOption_TTP() {
        resetOptions();
        parser.reset();
        int firstNonOptionArgIndex = parser.parse(options, new String[] {"--test"});
        Assert.assertEquals("Invalid first non-option argument index", -1, firstNonOptionArgIndex);
        String expectedErr = appName + ": invalid option -- 'test'\nTry '" + appName + " --help' for more information.";
        Assert.assertEquals("Wrong error msg", expectedErr, parser.getErrorMsg());
    }
    
    @Test
    public void ParserMissingArgLongOption_TTP() {
        resetOptions();
        parser.reset();
        int firstNonOptionArgIndex = parser.parse(options, new String[] {"--size"});
        String expectedErr = appName + ": option 'size' requires an argument\nTry '" + appName + " --help' for more information.";
        Assert.assertEquals("Invalid first non-option argument index", -1, firstNonOptionArgIndex);
        Assert.assertEquals("Wrong error msg", expectedErr, parser.getErrorMsg());
    }
    
    @Test
    public void ParserUnexpectedArgLongOption_TTP() {
        resetOptions();
        parser.reset();
        int firstNonOptionArgIndex = parser.parse(options, new String[] {"--verbose=true"});
        String expectedErr = appName + ": option 'verbose' does not require an argument\nTry '" + appName + " --help' for more information.";
        Assert.assertEquals("Invalid first non-option argument index", -1, firstNonOptionArgIndex);
        Assert.assertEquals("Wrong error msg", expectedErr, parser.getErrorMsg());
    }
    
    @Test
    public void ParserNoOptionsDetected_TTP() {
        resetOptions();
        parser.reset();
        int firstNonOptionArgIndex = parser.parse(options, new String[] {"file1 file2"});
        Assert.assertEquals("Invalid first non-option argument index", 0, firstNonOptionArgIndex);
    }
    
    @Test
    public void ParserNoOptionsDetectedNoArgs_TTP() {
        resetOptions();
        parser.reset();
        int firstNonOptionArgIndex = parser.parse(options, new String[] {});
        Assert.assertEquals("Invalid first non-option argument index", 0, firstNonOptionArgIndex);
    }
    
    
    /**
     * Reset the options
     */
    private void resetOptions() {
        optSize.setValue(null);
        optSize.setIsSet(false);
        optVerbose.setIsSet(false);
        optVersion.setIsSet(false);
        optHelp.setIsSet(false);
    }
}