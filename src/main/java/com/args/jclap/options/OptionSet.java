package com.args.jclap.options;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * This class describes a set of options. This set can be filled using a XML
 * file. A set is used to avoid duplicates
 */
public class OptionSet extends LinkedHashSet<Option> {

    private static final long serialVersionUID = 1L;

    /*
     **********************************************
     * Methods
     **********************************************
     */
    /**
     * Finds an option using its short name
     *
     * @param shortName The short name
     * @return The option which corresponds to the short name given in
     * parameter, null otherwise.
     */
    public Option getByShortName(char shortName) {
        Iterator<Option> it = this.iterator();
        while (it.hasNext()) {
            Option o = it.next();
            if (o.getShortName() == shortName) {
                return o;
            }
        }
        return null;
    }

    /**
     * Finds an option using its long name
     *
     * @param longName The long name
     * @return The option which corresponds to the long name given in parameter,
     * null otherwise.
     */
    public Option getByLongName(String longName) {
        Iterator<Option> it = this.iterator();
        while (it.hasNext()) {
            Option o = it.next();
            if (o.getLongName() != null && o.getLongName().equals(longName)) {
                return o;
            }
        }
        return null;
    }

    /**
     * Returns an array of the (not) set options. If the parameter is"true, the
     * array will contain all the set options. Otherwise, it will contain the
     * options not set.
     *
     * @param set "set" attribute value
     * @return An array of options
     */
    private Option[] getOptionsByStatus(boolean set) {
        List<Option> options = new LinkedList<>();
        Iterator<Option> it = this.iterator();
        while (it.hasNext()) {
            Option o = it.next();
            if (o.isSet() == set) {
                options.add(o);
            }
        }
        return options.toArray(new Option[0]);
    }

    /**
     * Returns an array of the defined options
     *
     * @return An array of options
     */
    public Option[] getSetOptions() {
        return getOptionsByStatus(true);
    }

    /**
     * Returns an array of the not defined options
     *
     * @return An array of options
     */
    public Option[] getNotSetOptions() {
        return getOptionsByStatus(false);
    }

    /**
     * Returns an array of the options help messages.
     *
     * @return An array of string (size can be 0 if there's no options)
     */
    public String[] getHelpMessages() {
        List<String> msgs = new ArrayList<>();
        Iterator<Option> it = this.iterator();
        while (it.hasNext()) {
            Option o = it.next();
            msgs.add(o.toString());
        }
        return msgs.toArray(new String[0]);
    }

    /**
     * Returns the help message generated using the options help messages.
     *
     * @return A string (cannot be null)
     */
    public String getHelpMessage() {
        return joinString(getHelpMessages(), "\n");
    }

    /**
     * Joins the array element in one string. These elements are separated by
     * the string sep.
     *
     * @param strs An array of string
     * @param sep The separator
     * @return A string which contains all the array elements separated by sep
     */
    private String joinString(String[] strs, String sep) {
        String help = "";
        int i;
        for (i = 0; i < strs.length - 1; i++) {
            help += strs[i] + sep;
        }
        help += strs[i];
        return help;
    }
}
