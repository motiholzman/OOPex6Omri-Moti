package oop.ex6.main.variables;

import oop.ex6.main.BadVariableException;
import oop.ex6.main.IllegalCodeException;

/**
 * {@inheritDoc}
 * this class represent a Boolean variable.
 */
public class BooleanVariable extends  Variable {

    /**
     * this constructor initialize the Boolean variable.
     * @param name : the name of the variable.
     * @param value: a value for the Variable.
     * @param isFinal: indicates whether the variable is consider final
     * @throws IllegalCodeException : in case of instance wasn't initialized correctly.
     */
    public BooleanVariable(String name, String value, Boolean isFinal) throws IllegalCodeException{
        super(name, value, isFinal);
        type = "Boolean";
    }

    @Override
    /**
     * {@inheritDoc}
     * this method checks whether the input for the variable is on of the following: true, false or any
     * number otherwise throws an exception.
     */
    public void checkVariable(String value) throws BadVariableException {
        try{
            if (value.equals("true") || value.equals("false")){
                return;
            }
            Double.parseDouble(value);
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
