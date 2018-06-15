package oop.ex6.main;

import java.io.IOException;

/**
 * this exception is thrown in case that an I/O exception occurred in Sjavac program. .
 */
public class InOutException extends IOException {

    private static final String IO_ERROR = "Error: there were an I/O error.";

    /**
     * a default constructor.
     * this constructor prints an informative message system err.
     */
    public InOutException() {
        System.err.println(InOutException.IO_ERROR);
    }
}
