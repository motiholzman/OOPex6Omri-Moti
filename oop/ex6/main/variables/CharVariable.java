package oop.ex6.main.variables;

import oop.ex6.main.BadVariableException;
import oop.ex6.main.IllegalCodeException;
import oop.ex6.main.Scope;

/**
 * {@inheritDoc}
 * this class represent a char variable.
 */
public class CharVariable extends Variable {

    /*this string is a regex that recognize char variable */
    private static final String MATCH_CHAR = "\'.\'";

    /**
     * this constructor initialize the char Variable.
     * @param name : the name of the variable.
     * @param value : a value for the Variable.
     * @param isFinal : indicates whether the variable is consider final
     * @param currentScope: the scope which the variable belongs to.
     * @throws IllegalCodeException : in case of instance wasn't initialized correctly.
     */
    public CharVariable(String name, String value, Boolean isFinal, Boolean isInitialize, Scope currentScope)
            throws IllegalCodeException {
        super(name,value,isFinal, isInitialize, currentScope);
        type = "Char";
    }


    /** {@inheritDoc} */
    public void checkVariable(String value, Scope currentScope) throws BadVariableException {
        if(!isVariableAssignmentValid(value)) {
            if (value.trim().matches(MATCH_CHAR)) {
                return;
            } else {
                throw new BadVariableException();
            }
        }
    }


}
