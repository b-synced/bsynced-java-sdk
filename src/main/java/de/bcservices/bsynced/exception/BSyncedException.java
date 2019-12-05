package de.bcservices.bsynced.exception;

/**
 * An exception class for this framework.
 *
 */
public class BSyncedException extends Exception {

    private static final long serialVersionUID = -7957145714136545771L;

    public BSyncedException(String msg) {
        super(msg);
    }

    public BSyncedException(String msg, Throwable exception) {
        super(msg, exception);
    }
}
