package oop.ex6.main.variables;

import oop.ex6.main.BadVariableException;
import oop.ex6.main.IllegalCodeException;
import oop.ex6.main.Scope;

/**
 * {@inheritDoc}
 * this class represent a Boolean variable.
 */
public class BooleanVariable extends  Variable {

    private static final String MATCH_DOUBLE = "-?(\\d)+([.](\\d*))?";
    private static final String MATCH_BOOLEAN = "true|false|"+ MATCH_DOUBLE;

    /**
     * this constructor initialize the Boolean variable.
     * @param name : the name of the variable.
     * @param value : a value for the Variable.
     * @param isFinal : indicates whether the variable is consider final
     * @param currentScope: the scope which the variable belongs to.
     * @throws IllegalCodeException : in case of instance wasn't initialized correctly.
     */
    public BooleanVariable(String name, String value, Boolean isFinal, Boolean isInitialize,
                           Scope currentScope) throws IllegalCodeException{
        super(name, value, isFinal, isInitialize, currentScope);
        type = "Boolean";
    }

    @Override
    /**
     * {@inheritDoc}
     * this method checks whether the input for the variable is on of the following: true, false or any
     * number otherwise throws an exception.
     */
    public void checkVariable(String value) throws BadVariableException {
        if (!isVariableAssignmentValid(value)) {
            if (value.trim().matches(MATCH_BOOLEAN)) {
                return;
            } else {
                throw new BadVariableException();
            }
        }
    }

//    @Override
//    public Variable copyValue(Variable variable) {
//        return null;
//    }
}
