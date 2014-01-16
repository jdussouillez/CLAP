package com.args.jclap.options;

import org.junit.Assert;
import org.junit.Test;

public class OptionSetTest {
    /*
     **********************************************
     * OptionSet get options by short/long name
     **********************************************
     */
    /*
     * Test-to-pass
     */

    @Test
    public void OptionSetGetOptionByShortName_TTP() throws InvalidOptionShortNameException,
                                                           InvalidOptionLongNameException,
                                                           InvalidOptionNamesException {
        OptionSet options = new OptionSet();
        Option opt1, opt2, opt3;
        opt1 = new Option('v', "version");
        opt2 = new Option('s', null);
        opt3 = new Option((char) 0, "help");
        options.add(opt1);
        options.add(opt2);
        options.add(opt3);
        Option o = options.getByShortName('s');
        Assert.assertTrue("No option found", o != null);
        Assert.assertTrue("Wrong option found", o == opt2);
    }

    @Test
    public void OptionSetGetOptionByShortNameNotFound_TTP() throws InvalidOptionShortNameException,
                                                                   InvalidOptionLongNameException,
                                                                   InvalidOptionNamesException {
        OptionSet options = new OptionSet();
        Option opt1, opt2, opt3;
        opt1 = new Option('v', "version");
        opt2 = new Option('s', null);
        opt3 = new Option((char) 0, "help");
        options.add(opt1);
        options.add(opt2);
        options.add(opt3);
        Option o = options.getByShortName('m');
        Assert.assertTrue("An option was found", o == null);
    }

    @Test
    public void OptionSetGetOptionByLongName_TTP() throws InvalidOptionShortNameException,
                                                          InvalidOptionLongNameException,
                                                          InvalidOptionNamesException {
        OptionSet options = new OptionSet();
        Option opt1, opt2, opt3;
        opt1 = new Option('v', "version");
        opt2 = new Option('s', null);
        opt3 = new Option((char) 0, "help");
        options.add(opt1);
        options.add(opt2);
        options.add(opt3);
        Option o = options.getByLongName("help");
        Assert.assertTrue("No option found", o != null);
        Assert.assertTrue("Wrong option found", o == opt3);
    }

    @Test
    public void OptionSetGetOptionByLongNameNotFound_TTP() throws InvalidOptionShortNameException,
                                                                  InvalidOptionLongNameException,
                                                                  InvalidOptionNamesException {
        OptionSet options = new OptionSet();
        Option opt1, opt2, opt3;
        opt1 = new Option('v', "version");
        opt2 = new Option('s', null);
        opt3 = new Option((char) 0, "help");
        options.add(opt1);
        options.add(opt2);
        options.add(opt3);
        Option o = options.getByLongName("size");
        Assert.assertTrue("An option was found", o == null);
    }

    /*
     **********************************************
     * OptionSet get options set/not set
     **********************************************
     */
    /*
     * Test-to-pass
     */
    @Test
    public void OptionSetGetSetOptions_TTP() throws InvalidOptionShortNameException,
                                                    InvalidOptionLongNameException,
                                                    InvalidOptionNamesException {
        OptionSet options = new OptionSet();
        Option opt1, opt2, opt3;
        opt1 = new Option('v', "version");
        opt2 = new Option('s', null);
        opt3 = new Option((char) 0, "help");
        options.add(opt1);
        options.add(opt2);
        options.add(opt3);
        opt1.setIsSet(true);
        opt3.setIsSet(true);
        Option[] setOptions = options.getSetOptions();
        Assert.assertEquals("Bad array size", 2, setOptions.length);
        Assert.assertEquals("Option should not be in array", opt1, setOptions[0]);
        Assert.assertEquals("Option should not be in array", opt3, setOptions[1]);
    }

    @Test
    public void OptionSetGetNotSetOptions_TTP() throws InvalidOptionShortNameException,
                                                       InvalidOptionLongNameException,
                                                       InvalidOptionNamesException {
        OptionSet options = new OptionSet();
        Option opt1, opt2, opt3;
        opt1 = new Option('v', "version");
        opt2 = new Option('s');
        opt3 = new Option("help");
        options.add(opt1);
        options.add(opt2);
        options.add(opt3);
        opt1.setIsSet(true);
        opt3.setIsSet(true);
        Option[] setOptions = options.getNotSetOptions();
        Assert.assertEquals("Bad array size", 1, setOptions.length);
        Assert.assertEquals("Option should not be in array", opt2, setOptions[0]);
    }

    /*
     **********************************************
     * Get the help message, and the joinString()
     **********************************************
     */
    @Test
    public void OptionSetGetHelpMessages_TTP() throws InvalidOptionShortNameException,
                                                      InvalidOptionLongNameException,
                                                      InvalidOptionNamesException {
        OptionSet options = new OptionSet();
        Option opt1, opt2, opt3;
        opt1 = new Option('q', "quiet", "Quiet output - only show errors");
        opt2 = new Option((char) 0, "version", "Output version information and exit");
        options.add(opt1);
        options.add(opt2);
        String[] helpMsgs = options.getHelpMessages();
        Assert.assertEquals("Invalid size of msgs array", 2, helpMsgs.length);
        Assert.assertEquals("Wrong msg #1", opt1.toString(), helpMsgs[0]);
        Assert.assertEquals("Wrong msg #2", opt2.toString(), helpMsgs[1]);
    }

    @Test
    public void OptionSetGetHelpMessage_TTP() throws InvalidOptionShortNameException,
                                                     InvalidOptionLongNameException,
                                                     InvalidOptionNamesException {
        OptionSet options = new OptionSet();
        Option opt1, opt2, opt3;
        opt1 = new Option('q', "quiet", "Quiet output - only show errors");
        opt2 = new Option((char) 0, "version", "Output version information and exit");
        options.add(opt1);
        options.add(opt2);
        String help = options.getHelpMessage();
        Assert.assertEquals(opt1.toString() + "\n" + opt2.toString(), help);
    }
}
