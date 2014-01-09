package com.args.clap.options;

/**
 * This class describes an option with no expected value. It can be defined with
 * a short and/or a long name, and a helpMessage message.
 */
public class Option {

    /**
     * Defines the pattern for an option's long name. The long name has to
     * contains at least 2 letters or digits.
     */
    public static final String LONGNAME_PATTERN = "[a-zA-Z0-9]{2,}";
    /*
     **********************************************
     * Attributes
     **********************************************
     */
    /**
     * Option's short name. For instance : 'v', detected in arguments as "-v".
     * Can be '\0' (= (char)0, it means this option has no short name), a letter
     * or a digit.
     */
    protected char shortName;
    /**
     * Option's long name. For instance : "version", detected in arguments as
     * "--version". Can be null. If not, it has to match the LONGNAME_PATTERN
     * pattern.
     */
    protected String longName;
    /**
     * A sentence to explain the option. Used to generate the helpMessage
     * message. (option "--help"). Can be null.
     */
    protected String helpMessage;
    /**
     * Indicates if the option was set or not. The default value is false, then
     * the parser set it to true if the option is detected in the arguments.
     */
    protected boolean set;

    /*
     **********************************************
     * Constructors
     **********************************************
     */
    /**
     * Creates a new instance of Option
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
    public Option(char shortName, String longName, String helpMessage) throws InvalidOptionShortNameException,
                                                                              InvalidOptionLongNameException,
                                                                              InvalidOptionNamesException {
        if (shortName != '\0' && !Character.isLetterOrDigit(shortName)) {
            throw new InvalidOptionShortNameException("Option creation : invalid short name \"" + shortName + "\"");
        }
        if (!isValidLongName(longName)) {
            throw new InvalidOptionLongNameException("Option creation : invalid long name \"" + longName + "\"");
        }
        if (shortName == 0 && longName == null) {
            throw new InvalidOptionNamesException("Option creation : an option has to contain at least a short or a long name");
        }
        this.shortName = shortName;
        this.longName = longName;
        this.helpMessage = helpMessage;
        this.set = false;
    }

    /**
     * Creates a new instance of Option
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
    public Option(char shortName, String longName) throws InvalidOptionShortNameException,
                                                          InvalidOptionLongNameException,
                                                          InvalidOptionNamesException {
        this(shortName, longName, null);
    }

    /**
     * Creates a new instance of Option
     *
     * @param shortName Option's short name.
     * @throws InvalidOptionShortNameException Thrown if the short name is not a
     * letter or a digit.
     * @throws InvalidOptionLongNameException Thrown if the long name does not
     * correspond to the pattern.
     * @throws InvalidOptionNamesException Thrown if both names are not
     * precised, 0 (short name) and null (long name).
     */
    public Option(char shortName) throws InvalidOptionShortNameException,
                                         InvalidOptionLongNameException,
                                         InvalidOptionNamesException {
        this(shortName, null, null);
    }

    /**
     * Creates a new instance of Option
     *
     * @param longName Option's long name.
     * @throws InvalidOptionShortNameException Thrown if the short name is not a
     * letter or a digit.
     * @throws InvalidOptionLongNameException Thrown if the long name does not
     * correspond to the pattern.
     * @throws InvalidOptionNamesException Thrown if both names are not
     * precised, 0 (short name) and null (long name).
     */
    public Option(String longName) throws InvalidOptionShortNameException,
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
     * Returns the option's short name
     *
     * @return Option's short name
     */
    public char getShortName() {
        return shortName;
    }

    /**
     * Returns the option's long name
     *
     * @return Option's long name
     */
    public String getLongName() {
        return longName;
    }

    /**
     * Returns the option status (set or not)
     *
     * @return true if the option was detected in the arguments, false otherwise
     */
    public boolean isSet() {
        return set;
    }

    /**
     * Set a new value for the "set" attribute
     *
     * @param set true if the option is set, false otherwise
     */
    public void setIsSet(boolean set) {
        this.set = set;
    }

    /**
     * Returns the option's helpMessage message
     *
     * @return Option's helpMessage message
     */
    public String getHelpMessage() {
        return helpMessage;
    }

    /**
     * Set a new help message
     *
     * @param helpMessage The new message
     */
    public void setHelpMessage(String helpMessage) {
        this.helpMessage = helpMessage;
    }

    /*
     **********************************************
     * Methods
     **********************************************
     */
    /**
     * Checks if the longname in parameter matches the longname regex pattern.
     *
     * @param longName The option's long name.
     * @return true if the longname is valid, false otherwise.
     */
    public static boolean isValidLongName(String longName) {
        return longName == null || (longName != null && longName.matches(LONGNAME_PATTERN));
    }

    /**
     * Build a string which describes the option.
     *
     * @return The option's description
     */
    @Override
    public String toString() {
        String res = "";
        if (shortName != 0) {
            res += "-" + shortName;
        }
        if (longName != null) {
            res += ((!res.isEmpty()) ? ", " : "") + "--" + longName;
        }
        if (helpMessage != null) {
            res += "\n\t" + helpMessage;
        }
        return res;
    }

    /**
     * Returns this option's hash code.
     *
     * @return A hash code for this option.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + shortName;
        hash = 43 * hash + (longName != null ? longName.hashCode() : 0);
        return hash;
    }

    /**
     * Indicates whether some other object is "equal to" this option.
     *
     * @param obj The reference object with which to compare.
     * @return true if this object is the same as the obj argument, false
     * otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Option other = (Option) obj;
        if (this.shortName != other.shortName) {
            return false;
        }
        return (this.longName == null) ? other.longName == null : longName.equals(other.longName);
    }
}
