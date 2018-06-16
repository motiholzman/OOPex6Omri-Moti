package oop.ex6.main.variables;

import oop.ex6.main.BadVariableException;
import oop.ex6.main.IllegalCodeException;

/**
 * {@inheritDoc}
 * this class represent a Double variable.
 */
public class DoubleVariable extends Variable {


    private static final String MATCH_DOUBLE = "-?(\\d)+[.](\\d*)";

    /**
     * this constructor initialize the Double Variable.
     * @param name : the name of the variable.
     * @param value: a value for the Variable.
     * @param isFinal: indicates whether the variable is consider final
     * @throws IllegalCodeException : in case of instance wasn't initialized correctly.
     */
    public DoubleVariable(String name, String value, Boolean isFinal, Boolean isInitialize)
            throws IllegalCodeException{
        super(name, value, isFinal,isInitialize);
        this.type = "double";
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void checkVariable(String value) throws BadVariableException {
        if(value.trim().matches(MATCH_DOUBLE)){
            return;
        }
        else {
            throw new BadVariableException();
        }
    }

//    @Override
//    public Variable copyValue(Variable variable) {
//        return null;
//    }
}
