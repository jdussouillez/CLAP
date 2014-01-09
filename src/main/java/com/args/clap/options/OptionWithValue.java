package com.args.clap.options;

/**
 * This class describes an option which expects a value. It can be defined with
 * a short and/or a long name, and a helpMessage message.
 */
public class OptionWithValue extends Option {

    public static final String DEFAULT_VALUE_NAME = "VALUE";
    /*
     **********************************************
     * Attributes
     **********************************************
     */
    /**
     * Option's value. For exemple : if the argument is "--size=10", the value
     * of the "size" option is 10
     */
    private String value;
    /**
     * Option's value name. Is used when the help message is generated. For
     * example : if the valueName attribute is set to "SIZE", the help message
     * will generate this kind of msg : -o SIZE, --optionName=SIZE
     */
    private String valueName;

    /*
     **********************************************
     * Constructors
     **********************************************
     */
    /**
     * Creates a new instance of OptionWithValue
     *
     * @param shortName Option's short name.
     * @param longName Option's long name.
     * @param helpMessage Option's helpMessage message.
     * @throws InvalidOptionShortNameException Thrown if the short name is not a
     * letter or a digit.
     * @throws InvalidOptionLongNameException Thrown if the long name does not
     * correspond to the pattern.
     * @throws InvalidOptionNamesException Thrown if both names are not
     * precised, 0 (short name) and null (long name).
     */
    public OptionWithValue(char shortName, String longName, String helpMessage) throws InvalidOptionShortNameException,
                                                                                       InvalidOptionLongNameException,
                                                                                       InvalidOptionNamesException {
        super(shortName, longName, helpMessage);
        value = null;
        valueName = null;
    }

    /**
     * Creates a new instance of OptionWithValue
     *
     * @param shortName Option's short name.
     * @param longName Option's long name.
     * @throws InvalidOptionShortNameException Thrown if the short name is not a
     * letter or a digit.
     * @throws InvalidOptionLongNameException Thrown if the long name does not
     * correspond to the pattern.
     * @throws InvalidOptionNamesException Thrown if both names are not
     * precised, 0 (short name) and null (long name).
     */
    public OptionWithValue(char shortName, String longName) throws InvalidOptionShortNameException,
                                                                   InvalidOptionLongNameException,
                                                                   InvalidOptionNamesException {
        this(shortName, longName, null);
    }

    /**
     * Creates a new instance of OptionWithValue
     *
     * @param shortName Option's short name.
     * @throws InvalidOptionShortNameException Thrown if the short name is not a
     * letter or a digit.
     * @throws InvalidOptionLongNameException Thrown if the long name does not
     * correspond to the pattern.
     * @throws InvalidOptionNamesException Thrown if both names are not
     * precised, 0 (short name) and null (long name).
     */
    public OptionWithValue(char shortName) throws InvalidOptionShortNameException,
                                                  InvalidOptionLongNameException,
                                                  InvalidOptionNamesException {
        this(shortName, null, null);
    }

    /**
     * Creates a new instance of OptionWithValue
     *
     * @param longName Option's long name.
     * @throws InvalidOptionShortNameException Thrown if the short name is not a
     * letter or a digit.
     * @throws InvalidOptionLongNameException Thrown if the long name does not
     * correspond to the pattern.
     * @throws InvalidOptionNamesException Thrown if both names are not
     * precised, 0 (short name) and null (long name).
     */
    public OptionWithValue(String longName) throws InvalidOptionShortNameException,
                                                   InvalidOptionLongNameException,
                                                   InvalidOptionNamesException {
        this((char) 0, longName, null);
    }

    /*
     **********************************************
     * Getters and setters
     **********************************************
     */
    /**
     * Returns the option value
     *
     * @return The value of the option, null otherwise.
     */
    public String getValue() {
        return value;
    }

    /**
     * Set a new value for this option
     *
     * @param set The new value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Set a new value name for this option (for the help message)
     *
     * @param valueName The name of the value
     */
    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    /**
     * Build a string which describes the option.
     *
     * @return The option's description
     */
    @Override
    public String toString() {
        String res = "", valName = (valueName == null) ? DEFAULT_VALUE_NAME : valueName;
        if (shortName != 0) {
            res += "-" + shortName + " " + valName;
        }
        if (longName != null) {
            res += ((!res.isEmpty()) ? ", " : "") + "--" + longName + "=" + valName;
        }
        if (helpMessage != null) {
            res += "\n\t" + helpMessage;
        }
        return res;
    }
}
