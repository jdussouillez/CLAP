package org.jd.jclap.options;

public class InvalidOptionShortNameException extends OptionCreationException {

    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_MSG = "Option creation: invalid short name";

    public InvalidOptionShortNameException(String msg) {
        super(msg);
    }

    public InvalidOptionShortNameException() {
        super(DEFAULT_MSG);
    }
}
