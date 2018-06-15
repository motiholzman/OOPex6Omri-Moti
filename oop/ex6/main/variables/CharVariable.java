package oop.ex6.main.variables;

import oop.ex6.main.BadVariableException;
import oop.ex6.main.IllegalCodeException;

/**
 * {@inheritDoc}
 * this class represent a char variable.
 */
public class CharVariable extends Variable {

    /**
     * this constructor initialize the char Variable.
     * @param name : the name of the variable.
     * @param value: a value for the Variable.
     * @param isFinal: indicates whether the variable is consider final
     * @throws IllegalCodeException : in case of instance wasn't initialized correctly.
     */
    public CharVariable(String name, String value, Boolean isFinal) throws IllegalCodeException {
        super(name,value,isFinal);
        type = "Char";
    }

    @Override
    /** {@inheritDoc} */
    public void checkVariable(String value) throws BadVariableException {
        if (value.length() == 1) { //FIXME isn't enough - we have to check for a regex '.'
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
