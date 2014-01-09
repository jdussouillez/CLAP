package com.args.clap.options;

public class InvalidOptionLongNameException extends OptionCreationException {

    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_MSG = "Option creation: invalid long name";

    public InvalidOptionLongNameException(String msg) {
        super(msg);
    }

    public InvalidOptionLongNameException() {
        super(DEFAULT_MSG);
    }
}
