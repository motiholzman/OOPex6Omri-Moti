package oop.ex6.main;

public class BadScopeSignatureException extends IllegalCodeException {

    private static final String SIGNATURE_ERROR = "Error: the Scope's signature isn't in the correct " +
            "foramt.";

    /**
     * a default constructor.
     * this constructor prints an informative message system err.
     */
    public BadScopeSignatureException() {
        System.err.println(BadScopeSignatureException.SIGNATURE_ERROR);
    }
}
