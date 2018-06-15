package oop.ex6.main;

/**
 * {@inheritDoc}
 * this exception is thrown in case that a Variable instance wasn't initialized correctly.
 */
public class BadVariableException extends IllegalCodeException {

    private static final String INIT_ERROR = "Error: thee Variable wasn't initialized correctly.";

    /**
     * a default constructor.
     * this constructor prints an informative message system err.
     */
    public BadVariableException() {
        System.err.println(BadVariableException.INIT_ERROR);
    }
}
