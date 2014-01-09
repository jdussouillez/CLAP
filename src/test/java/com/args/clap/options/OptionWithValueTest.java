package com.args.clap.options;

import org.junit.Assert;
import org.junit.Test;

public class OptionWithValueTest {

    /*
     **********************************************
     * OptionWithValue creation
     **********************************************
     */
    /*
     * Test-to-pass
     */
    @Test
    public void OptionWithValueCreation_TTP() throws InvalidOptionShortNameException,
                                                     InvalidOptionLongNameException,
                                                     InvalidOptionNamesException {
        OptionWithValue o = new OptionWithValue('s', null);
        Assert.assertEquals("Not the same short name", 's', o.getShortName());
        Assert.assertEquals("Long name not null", null, o.getLongName());
        Assert.assertEquals("Help message not null", null, o.getHelpMessage());
        Assert.assertEquals("Value not null", null, o.getValue());
    }

    @Test
    public void OptionWithValueCreationOnlyShortName_TTP() throws InvalidOptionShortNameException,
                                                                  InvalidOptionLongNameException,
                                                                  InvalidOptionNamesException {
        OptionWithValue o = new OptionWithValue('s');
        Assert.assertEquals("Not the same short name", 's', o.getShortName());
        Assert.assertEquals("Long name not null", null, o.getLongName());
        Assert.assertEquals("Help message not null", null, o.getHelpMessage());
        Assert.assertEquals("Value not null", null, o.getValue());
    }

    @Test
    public void OptionWithValueCreationOnlyLongName_TTP() throws InvalidOptionShortNameException,
                                                                 InvalidOptionLongNameException,
                                                                 InvalidOptionNamesException {
        OptionWithValue o = new OptionWithValue("size");
        Assert.assertEquals("Not the same short name", '\0', o.getShortName());
        Assert.assertEquals("Long name not null", "size", o.getLongName());
        Assert.assertEquals("Help message not null", null, o.getHelpMessage());
        Assert.assertEquals("Value not null", null, o.getValue());
    }

    /*
     **********************************************
     * OptionWithValue, set/get value
     **********************************************
     */
    /*
     * Test-to-pass
     */
    @Test
    public void OptionWithValueSetAndGetValue_TTP() throws InvalidOptionShortNameException,
                                                           InvalidOptionLongNameException,
                                                           InvalidOptionNamesException {
        OptionWithValue o = new OptionWithValue('v', null);
        o.setValue("val");
        Assert.assertEquals("setValue() failed", "val", o.getValue());
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
    public void OptionWithValueToStringOnlyShortName_TTP() throws InvalidOptionShortNameException,
                                                   InvalidOptionLongNameException,
                                                   InvalidOptionNamesException {
        Assert.assertEquals("-s " + OptionWithValue.DEFAULT_VALUE_NAME, (new OptionWithValue('s')).toString());
    }

    @Test
    public void OptionWithValueToStringOnlyLongName_TTP() throws InvalidOptionShortNameException,
                                                  InvalidOptionLongNameException,
                                                  InvalidOptionNamesException {
        Assert.assertEquals("--size=" + OptionWithValue.DEFAULT_VALUE_NAME, (new OptionWithValue("size")).toString());
    }

    @Test
    public void OptionWithValueToStringShortAndLongName_TTP() throws InvalidOptionShortNameException,
                                                      InvalidOptionLongNameException,
                                                      InvalidOptionNamesException {
        OptionWithValue o = new OptionWithValue('s', "size");
        String msg = "-s " + OptionWithValue.DEFAULT_VALUE_NAME + ", --size=" + OptionWithValue.DEFAULT_VALUE_NAME;
        Assert.assertEquals(msg, o.toString());
    }

    @Test
    public void OptionWithValueToStringShortAndLongNameWithHelpMessage_TTP() throws InvalidOptionShortNameException,
                                                                     InvalidOptionLongNameException,
                                                                     InvalidOptionNamesException {
        String helpMsg = "This is a help message";
        OptionWithValue o = new OptionWithValue('s', "size", helpMsg);
        String toStringOutput = "-s " + OptionWithValue.DEFAULT_VALUE_NAME + ", --size=" + OptionWithValue.DEFAULT_VALUE_NAME + "\n\t" + helpMsg;
        Assert.assertEquals(toStringOutput, o.toString());
    }
    
    @Test
    public void OptionWithValueToStringValueNameChanged_TTP() throws InvalidOptionShortNameException,
                                                      InvalidOptionLongNameException,
                                                      InvalidOptionNamesException {
        OptionWithValue o = new OptionWithValue('s');
        o.setValueName("SIZE");
        Assert.assertEquals("-s SIZE", o.toString());
    }
}
