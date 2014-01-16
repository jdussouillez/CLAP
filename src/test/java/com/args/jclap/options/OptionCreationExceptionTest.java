package com.args.jclap.options;

import org.junit.Assert;
import org.junit.Test;

public class OptionCreationExceptionTest {

    /*
     **********************************************
     * InvalidOptionShortNameException
     **********************************************
     */
    /*
     * Test-to-pass
     */
    @Test
    public void InvalidOptionShortNameExceptionDefaultMessage_TTP() {
        InvalidOptionShortNameException ex = new InvalidOptionShortNameException();
        Assert.assertEquals("Wrong message", InvalidOptionShortNameException.DEFAULT_MSG, ex.getMessage());
    }

    @Test
    public void InvalidOptionShortNameException_TTP() {
        String msg = "test";
        InvalidOptionShortNameException ex = new InvalidOptionShortNameException(msg);
        Assert.assertEquals("Wrong message", msg, ex.getMessage());
    }

    /*
     **********************************************
     * InvalidOptionLongNameException
     **********************************************
     */
    /*
     * Test-to-pass
     */
    @Test
    public void InvalidOptionLongNameExceptionDefaultMessage_TTP() {
        InvalidOptionLongNameException ex = new InvalidOptionLongNameException();
        Assert.assertEquals("Wrong message", InvalidOptionLongNameException.DEFAULT_MSG, ex.getMessage());
    }

    @Test
    public void InvalidOptionLongNameException_TTP() {
        String msg = "test";
        InvalidOptionLongNameException ex = new InvalidOptionLongNameException(msg);
        Assert.assertEquals("Wrong message", msg, ex.getMessage());
    }

    /*
     **********************************************
     * InvalidOptionLongNameException
     **********************************************
     */
    /*
     * Test-to-pass
     */
    @Test
    public void InvalidOptionNamesExceptionDefaultMessage_TTP() {
        InvalidOptionNamesException ex = new InvalidOptionNamesException();
        Assert.assertEquals("Wrong message", InvalidOptionNamesException.DEFAULT_MSG, ex.getMessage());
    }

    @Test
    public void InvalidOptionNamesException_TTP() {
        String msg = "test";
        InvalidOptionNamesException ex = new InvalidOptionNamesException(msg);
        Assert.assertEquals("Wrong message", msg, ex.getMessage());
    }
}
