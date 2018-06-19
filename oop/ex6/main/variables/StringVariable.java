package oop.ex6.main.variables;

import oop.ex6.main.BadVariableException;
import oop.ex6.main.IllegalCodeException;
import oop.ex6.main.Scope;

/**
 * {@inheritDoc}
 * this class represent a String variable.
 */
public class StringVariable extends Variable {

    /*this string is a regex that recognize string variable */
    private static final String MATCH_STRING = "\"[^>]*\"";

    /**
     * this constructor initialize the string Variable.
     * @param name : the name of the variable.
     * @param value : a value for the Variable.
     * @param isFinal : indicates whether the variable is consider final
     * @param currentScope: the scope which the variable belongs to.
     * @throws IllegalCodeException : in case of instance wasn't initialized correctly.
     */
    public StringVariable(String name, String value, Boolean isFinal, Boolean isInitialize,
                          Scope currentScope)
            throws IllegalCodeException {
        super(name,value, isFinal, isInitialize, currentScope);
        type = "String";
    }


    /**
     * {@inheritDoc}
     * we have no constraints on this value.
     */
    public void checkAndAssignVariable(String value, Scope currentScope) throws BadVariableException {
        Variable otherVariable = (isVariableAssignmentValid(value, currentScope));
        if (otherVariable == null)
        {
            if (value.trim().matches(MATCH_STRING)) {
                this.value = value;
                this.isInitialize = true;
                return;
            } else {
                throw new BadVariableException();
            }
        }
        else {
            this.value = otherVariable.value;
            this.isInitialize = true;
        }

    }

}
