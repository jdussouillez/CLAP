package org.jd.jclap.options;

import org.junit.Assert;
import org.junit.Test;

public class OptionTest {

    /*
     **********************************************
     * Option creation
     **********************************************
     */
    /*
     * Test-to-pass
     */
    @Test
    public void CreationOnlyShortName_TTP() throws InvalidOptionShortNameException,
                                                   InvalidOptionLongNameException,
                                                   InvalidOptionNamesException {
        Option o = new Option('v');
        Assert.assertEquals("Not the same short name", 'v', o.getShortName());
        Assert.assertEquals("Long name not null", null, o.getLongName());
        Assert.assertEquals("Help message not null", null, o.getHelpMessage());
        Assert.assertFalse("\"set\" attribute is not false", o.isSet());
    }

    @Test
    public void CreationOnlyLongName_TTP() throws InvalidOptionShortNameException,
                                                  InvalidOptionLongNameException,
                                                  InvalidOptionNamesException {
        Option o = new Option("version");
        Assert.assertEquals("Short name not 0", (char) 0, o.getShortName());
        Assert.assertEquals("Not the same long name", "version", o.getLongName());
        Assert.assertEquals("Help message not null", null, o.getHelpMessage());
        Assert.assertFalse("\"set\" attribute is not false", o.isSet());
    }

    @Test
    public void CreationOnlyLongNameWithHelp_TTP() throws InvalidOptionShortNameException,
                                                          InvalidOptionLongNameException,
                                                          InvalidOptionNamesException {
        Option o = new Option('v', "version", "output version information and exit");
        Assert.assertEquals("Not the same short name", 'v', o.getShortName());
        Assert.assertEquals("Not the same long name", "version", o.getLongName());
        Assert.assertEquals("Not the same help message", "output version information and exit", o.getHelpMessage());
        Assert.assertFalse("\"set\" attribute is not false", o.isSet());
    }

    /*
     * Test-to-fail
     */
    @Test(expected = InvalidOptionShortNameException.class)
    public void CreationInvalidShortName_TTF() throws InvalidOptionShortNameException,
                                                      InvalidOptionLongNameException,
                                                      InvalidOptionNamesException {
        Option o = new Option('-', null);
    }

    @Test(expected = InvalidOptionLongNameException.class)
    public void CreationInvalidLongName_TTF() throws InvalidOptionShortNameException,
                                                     InvalidOptionLongNameException,
                                                     InvalidOptionNamesException {
        Option o = new Option('\0', "a");
    }

    @Test(expected = InvalidOptionNamesException.class)
    public void CreationInvalidNames_TTF() throws InvalidOptionShortNameException,
                                                  InvalidOptionLongNameException,
                                                  InvalidOptionNamesException {
        Option o = new Option('\0', null);
    }

    /*
     **********************************************
     * isValidLongName()
     **********************************************
     */
    /*
     * Test-to-pass
     */
    @Test
    public void nullLongName_TTP() {
        Assert.assertTrue(Option.isValidLongName(null));
    }

    @Test
    public void validLongName_TTP() {
        Assert.assertTrue(Option.isValidLongName("version"));
    }

    @Test
    public void emptyLongName_TTP() {
        Assert.assertFalse(Option.isValidLongName(""));
    }

    @Test
    public void oneCharacterLongName_TTP() {
        Assert.assertFalse(Option.isValidLongName("v"));
    }

    @Test
    public void invalidCharacterLongName_TTP() {
        Assert.assertFalse(Option.isValidLongName("sortby?name"));
    }

    /*
     **********************************************
     * toString()
     **********************************************
     */
    /*
     * Test-to-pass
     */
    @Test
    public void OptionToStringOnlyShortName_TTP() throws InvalidOptionShortNameException,
                                                         InvalidOptionLongNameException,
                                                         InvalidOptionNamesException {
        Assert.assertEquals("-v", (new Option('v', null)).toString());
    }

    @Test
    public void OptionToStringOnlyLongName_TTP() throws InvalidOptionShortNameException,
                                                        InvalidOptionLongNameException,
                                                        InvalidOptionNamesException {
        Assert.assertEquals("--version", (new Option('\0', "version")).toString());
    }

    @Test
    public void OptionToStringShortAndLongName_TTP() throws InvalidOptionShortNameException,
                                                            InvalidOptionLongNameException,
                                                            InvalidOptionNamesException {
        Assert.assertEquals("-v, --version", (new Option('v', "version")).toString());
    }

    @Test
    public void OptionToStringShortAndLongNameWithHelpMessage_TTP() throws InvalidOptionShortNameException,
                                                                           InvalidOptionLongNameException,
                                                                           InvalidOptionNamesException {
        String msg = "test";
        Assert.assertEquals("-v, --version\n\t" + msg, (new Option('v', "version", msg)).toString());
    }

    /*
     **********************************************
     * setters
     **********************************************
     */
    /*
     * Test-to-pass
     */
    @Test
    public void setHelpMessage_TTP() throws InvalidOptionShortNameException,
                                            InvalidOptionLongNameException,
                                            InvalidOptionNamesException {
        Option o = new Option("version");
        o.setHelpMessage("ThisIsAHelpMessage");
        Assert.assertEquals("Invalid help message", "ThisIsAHelpMessage", o.getHelpMessage());
    }

    /*
     **********************************************
     * hashCode()
     **********************************************
     */
    /*
     * Test-to-pass
     */
    @Test
    public void hashCodeNoLongName_TTP() throws InvalidOptionShortNameException,
                                                InvalidOptionLongNameException,
                                                InvalidOptionNamesException {
        Assert.assertEquals(18017, (new Option('v', null)).hashCode());
    }

    @Test
    public void hashCodeLongName_TTP() throws InvalidOptionShortNameException,
                                              InvalidOptionLongNameException,
                                              InvalidOptionNamesException {
        Assert.assertEquals(351626041, (new Option('v', "version")).hashCode());
    }

    /*
     **********************************************
     * equals()
     **********************************************
     */
    /*
     * Test-to-pass
     */
    @Test
    public void equalsNullOption_TPP() throws InvalidOptionShortNameException,
                                              InvalidOptionLongNameException,
                                              InvalidOptionNamesException {
        Option o1 = new Option('v', "version");
        Assert.assertFalse(o1.equals(null));
    }

    @Test
    public void equalsNotAnOption_TPP() throws InvalidOptionShortNameException,
                                               InvalidOptionLongNameException,
                                               InvalidOptionNamesException {
        Option o1 = new Option('v', "version");
        Assert.assertFalse(o1.equals(new String("test")));
    }

    @Test
    public void equalsDifferentShortName_TPP() throws InvalidOptionShortNameException,
                                                      InvalidOptionLongNameException,
                                                      InvalidOptionNamesException {
        Option o1, o2;
        o1 = new Option('v', null);
        o2 = new Option('f', null);
        Assert.assertFalse(o1.equals(o2));
    }

    @Test
    public void equalsDifferentLongName_TPP() throws InvalidOptionShortNameException,
                                                     InvalidOptionLongNameException,
                                                     InvalidOptionNamesException {
        Option o1, o2;
        o1 = new Option('\0', "version");
        o2 = new Option('\0', "help");
        Assert.assertFalse(o1.equals(o2));
    }

    @Test
    public void equalsSameOption_TPP() throws InvalidOptionShortNameException,
                                              InvalidOptionLongNameException,
                                              InvalidOptionNamesException {
        Option o1, o2;
        o1 = new Option('v', "version");
        o2 = new Option('v', "version");
        Assert.assertTrue(o1.equals(o2));
    }

    /*
     **********************************************
     * set(), isSet()
     **********************************************
     */
    /*
     * Test-to-pass
     */
    @Test
    public void isSetAndSet_TPP() throws InvalidOptionShortNameException,
                                         InvalidOptionLongNameException,
                                         InvalidOptionNamesException {
        Option o1 = new Option('v', "version");
        o1.setIsSet(true);
        Assert.assertTrue(o1.isSet());
    }
}
