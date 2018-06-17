package oop.ex6.main;


/**
 * this class represents an error of the Sjavac code.
 */
public class BadCodeException extends IllegalCodeException {

    /**
     * a default constructor.
     * this constructor prints a given informative message to system err.
     */
    public BadCodeException(String message) {
        System.err.println(message);
    }
}
