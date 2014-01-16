package com.args.jclap.options;

public abstract class OptionCreationException extends Exception {

    private static final long serialVersionUID = 1L;

    public OptionCreationException(String msg) {
        super(msg);
    }
}
