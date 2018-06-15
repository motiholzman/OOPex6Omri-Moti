package oop.ex6.main.variables;

import oop.ex6.main.BadVariableException;
import oop.ex6.main.IllegalCodeException;

/**
 * {@inheritDoc}
 * this class represent a Double variable.
 */
public class IntVariable extends Variable {

    /**
     * this constructor initialize the string Variable.
     * @param name : the name of the variable.
     * @param value: a value for the Variable.
     * @param isFinal: indicates whether the variable is consider final
     * @throws IllegalCodeException : in case of instance wasn't initialized correctly.
     */
    public IntVariable(String name, String value, Boolean isFinal) throws IllegalCodeException {
        super(name, value, isFinal);
        this.type = "int";
    }

    @Override
    /**
     * {@inheritDoc}
     * this method checks that the given value is an integer.
     */
    public void checkVariable(String value) throws BadVariableException{
        try{
            Integer.parseInt(value);
        }
        catch (NumberFormatException e){
            throw new BadVariableException();
        }
    }

//    @Override
//    public Variable copyValue(Variable variable) {
//        return null;
//    }
}
