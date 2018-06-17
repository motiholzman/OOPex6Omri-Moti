package oop.ex6.main.variables;

import oop.ex6.main.BadVariableException; //FIXME think about the location..
import oop.ex6.main.IllegalCodeException;

import java.util.regex.Matcher;

/**
 * this class represent a variable in Sjavac program.
 */
public abstract class Variable {

    /** the Variable's type*/
    protected String type;

    /** the Variable's name*/
    private String name;

    /** indicates whether the variable is initialize.*/
    private Boolean isInitialize;

    /** indicates whether the variable is consider final.*/
    private Boolean isFinal;

    private final String MATCH_NAME = "([a-zA-Z]|_)+\\w";

    public Boolean variableTypeMatch(String type){
        return this.type.equals(type);
    }


    /**
     * this constructor initialize the Variable.
     * @param name : the name of the variable.
     * @param value: a value for the Variable.
     * @param isFinal: indicates whether the variable is consider final
     * @throws IllegalCodeException : in case of instance wasn't initialized correctly.
     */
    public Variable(String name, String value, Boolean isFinal, Boolean isInitialize)
            throws IllegalCodeException {
        this.isFinal = isFinal;
        this.isInitialize = isInitialize;
        try{
            checkName(name);
            checkVariable(value);
        }
        catch (BadVariableException e) {
            throw new IllegalCodeException();
        }
    }

//    /**
//     * a copy constructor.
//     * @param variable : an authenticated variable to copy.
//     */
//    public Variable(Variable variable){
//        this.isInitialize =
//    }

    /**
     * this method checks if the variable is initialize correctly according to it's definitions.
     * @param value : the variable assignment value.
     * @throws BadVariableException : to indicates whether a problem detected.
     */
    public abstract void checkVariable(String value) throws BadVariableException;

    //public abstract Variable copyValue(Variable variable);

    /*
    this private method checks whether the name of the variable is correct.
     */
    private void checkName(String name) throws BadVariableException {
        if(name.trim().matches(MATCH_NAME)) {
            this.name = name;
        }
        else {
            throw new BadVariableException ();
        }
    }

    /**
     * @return : the variable's name.
     */
    public String getName() {
        return name;
    }


}
