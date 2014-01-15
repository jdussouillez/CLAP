package com.args.clap.parser;

import com.args.clap.options.Option;
import com.args.clap.options.OptionSet;
import com.args.clap.options.OptionWithValue;

public class Parser {

    /*
     **********************************************
     * The differents type of errors
     **********************************************
     */
    /**
     * The option does not exist in the option set
     */
    private static final int INVALID_OPTION = 1;
    /**
     * The option does not require an argument
     */
    private static final int NO_ARG_VALUE = 2;
    /**
     * The option do require an argument
     */
    private static final int NEEDS_ARG_VALUE = 3;
    /**
     * Unexpected argument (a non-option argument between options)
     */
    private static final int UNEXPECTED_ARG = 4;
    /*
     **********************************************
     * Attributes
     **********************************************
     */
    /**
     * Error message. Set by the parser when a error is detected.
     */
    private String errorMsg;
    /**
     * Name of the application. This name is used to generate the error messages
     */
    private String appName;

    /*
     **********************************************
     * Constructor
     **********************************************
     */
    /**
     * Creates a new instance of Parser
     *
     * @param appName Name of the application
     */
    public Parser(String appName) {
        this.appName = appName;
    }

    /*
     **********************************************
     * Getters
     **********************************************
     */
    /**
     * Returns the error message
     *
     * @return The error message, null if there is no error
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /*
     **********************************************
     * Methods
     **********************************************
     */
    /**
     * Parse the arguments. At the end, the options (param) are changed
     * (attribute : "set" and "value"). Returns the first non-option argument
     * index (in the args array), -1 in an error was detected.
     *
     * @param options The option collection
     * @param args The arguments
     * @return The first non-option argument index (last option index + 1), -1
     * if an error was detected
     */
    public int parse(OptionSet options, String[] args) {
        int i, lastOptionIndex = -1;
        boolean nonOptionArgDetected = false;
        for (i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '-') {
                if (args[i].length() == 1) {
                    log("-", INVALID_OPTION);
                    return -1;
                }
                if (nonOptionArgDetected) {
                    // This arg is an option, but the previous arg was not,
                    // it's an error (this arg is unexpected)
                    log(args[i], UNEXPECTED_ARG);
                    return -1;
                }
                Option opt;
                if (args[i].charAt(1) != '-') {
                    /*
                     * Short name option(s)
                     */
                    char shortName;
                    for (int j = 1; j < args[i].length() - 1; j++) {
                        // In this loop, the options are in the same "block"
                        // For instance : "-abc", here we analyse "a" and "b"
                        // These 2 options can't have an argument, only the last option can (next argument)
                        shortName = args[i].charAt(j);
                        opt = options.getByShortName(shortName);
                        if (opt == null) {
                            log(String.valueOf(shortName), INVALID_OPTION);
                            return -1;
                        }
                        if (opt instanceof OptionWithValue) {
                            // There is only options without argument in this loop
                            log(String.valueOf(shortName), NEEDS_ARG_VALUE);
                            return -1;
                        }
                        opt.setIsSet(true);
                    }
                    // Last option (ex : 's' in "-s" or 'c' in "-abc")
                    // This option can have an argument
                    shortName = args[i].charAt(args[i].length() - 1);
                    opt = options.getByShortName(shortName);
                    if (opt == null) {
                        log(String.valueOf(shortName), INVALID_OPTION);
                        return -1;
                    }
                    if (opt instanceof OptionWithValue) {
                        if (i >= args.length - 1) {
                            log(String.valueOf(shortName), NEEDS_ARG_VALUE);
                            return -1;
                        }
                        ((OptionWithValue) opt).setValue(args[++i]);
                    }
                    opt.setIsSet(true);
                }
                else {
                    /*
                     * Long name option
                     */
                    String longName, value = null;
                    longName = args[i].substring(2);
                    // Split the option name and the value
                    int equalSignIndex = args[i].indexOf('='); // index of '=', -1 if it not is this string
                    if (equalSignIndex > -1) {
                        // The argument is an option with argument
                        longName = args[i].substring(2, equalSignIndex);
                        value = args[i].substring(equalSignIndex + 1);
                    }
                    opt = options.getByLongName(longName);
                    if (opt == null) {
                        log(longName, INVALID_OPTION);
                        return -1;
                    }
                    if (opt instanceof OptionWithValue) {
                        if (value == null) {
                            // The option expected an argument, but it was not specified
                            log(longName, NEEDS_ARG_VALUE);
                            return -1;
                        }
                        ((OptionWithValue) opt).setValue(value);
                    }
                    else if (value != null) {
                        // The option does not expect an argument, but there is one
                        log(longName, NO_ARG_VALUE);
                        return -1;
                    }
                    opt.setIsSet(true);
                }
                lastOptionIndex = i;
            }
            else {
                // This arg is not an option
                nonOptionArgDetected = true;
            }
        }
        return lastOptionIndex + 1; // First non-option argument
    }

    /**
     * Set the error message.
     *
     * @param opt The option name which is the source of the error
     * @param errorType The type of the error
     */
    private void log(String opt, int errorType) {
        errorMsg = appName + ": ";
        switch (errorType) {
            case INVALID_OPTION:
                errorMsg += "invalid option -- '" + opt + "'";
                break;
            case NO_ARG_VALUE:
                errorMsg += "option '" + opt + "' does not require an argument";
                break;
            case NEEDS_ARG_VALUE:
                errorMsg += "option '" + opt + "' requires an argument";
                break;
            case UNEXPECTED_ARG:
                errorMsg += "unexpected option '" + opt + "'";
                break;
        }
        errorMsg += "\nTry '" + appName + " --help' for more information.";
    }

    /**
     * Reset the state of the parser. Set the error message to null
     */
    public void reset() {
        errorMsg = null;
    }
}
