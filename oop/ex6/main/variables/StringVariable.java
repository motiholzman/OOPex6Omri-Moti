package oop.ex6.main.variables;

import oop.ex6.main.BadVariableException;
import oop.ex6.main.IllegalCodeException;

/**
 * {@inheritDoc}
 * this class represent a String variable.
 */
public class StringVariable extends Variable {

    /**
     * this constructor initialize the string Variable.
     * @param name : the name of the variable.
     * @param value: a value for the Variable.
     * @param isFinal: indicates whether the variable is consider final
     * @throws IllegalCodeException : in case of instance wasn't initialized correctly.
     */
    public StringVariable(String name, String value, Boolean isFinal) throws IllegalCodeException{
        super(name,value,isFinal);
        type = "String";
    }

    @Override
    /**
     * {@inheritDoc}
     * we have no constraints on this value.
     */
    public void checkVariable(String value) throws BadVariableException {
    }

//    @Override
//    /**
//     * {@inheritDoc}
//     */
//    public Variable copyValue(Variable variable) {
//        return null;
//    }
}
