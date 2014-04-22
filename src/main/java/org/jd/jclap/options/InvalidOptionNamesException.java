package org.jd.jclap.options;

public class InvalidOptionNamesException extends OptionCreationException {

    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_MSG = "Option creation: invalid names (has to contains at least one valid name)";

    public InvalidOptionNamesException(String msg) {
        super(msg);
    }

    public InvalidOptionNamesException() {
        super(DEFAULT_MSG);
    }
}
