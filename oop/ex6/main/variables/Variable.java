package oop.ex6.main.variables;

import oop.ex6.main.BadVariableException; //FIXME think about the location..
import oop.ex6.main.IllegalCodeException;
import oop.ex6.main.Scope;


/**
 * this class represent a variable in Sjavac program.
 */
public abstract class Variable {

    /** the Variable's type*/
    protected String type;

    /* the Variable's name*/
    private String name;

    /* indicates whether the variable is initialize.*/
    private Boolean isInitialize;

    /* indicates whether the variable is consider final.*/
    private Boolean isFinal;

    /* a regex that checks that the variable's name is in the correct pattern. */
    private final String MATCH_NAME = "([a-zA-Z]|_)+\\w*";

    /* the Variable's scopes which it belong to. */
    private Scope variableScope;


    /**
     * this constructor initialize the Variable.
     * @param name : the name of the variable.
     * @param value : a value for the Variable.
     * @param isFinal : indicates whether the variable is consider final
     * @param currentScope: the scope which the variable belongs to.
     * @throws IllegalCodeException : in case of instance wasn't initialized correctly.
     */
    public Variable(String name, String value, Boolean isFinal, Boolean isInitialize, Scope currentScope)
            throws IllegalCodeException {
        this.isFinal = isFinal;
        this.isInitialize = isInitialize;
        try{
            checkName(name);
            if(isInitialize) {
                checkVariable(value);
            }
        }
        catch (BadVariableException e) {
            throw new IllegalCodeException();
        }
    }

    /**
     * a function that receives a String and checks whether the given type is equal to the variable's type.
     * @param type: a type to compare.
     * @return : true if it matches, false otherwise.
     */
    public Boolean variableTypeMatch(String type) {
        return this.type.equals(type);
    }


    /**
     * this method checks if the variable is initialize correctly according to it's definitions.
     * @param value : the variable assignment value.
     * @throws BadVariableException : to indicates whether a problem detected.
     */
    public abstract void checkVariable(String value) throws BadVariableException;

    /**
     * this method checks if there is a variable with the given name in this scope or some other scope, and
     * whether this assignment is legal: the type of the variables are match, and the assigned variable is
     * initialize.
     * @param variableName : a given name for search.
     * @return : true if the assignment is legal, false if the variable wasn't found.
     * @throws BadVariableException: in case that the assignment isn't legal.
     */
    protected boolean isVariableAssignmentValid(String variableName) throws BadVariableException {
        Variable otherVariable = variableScope.getVariable(variableName);
        if (otherVariable == null) {
            return false;
        }
        if ((otherVariable.isInitialize) && otherVariable.type.equals(type)) {
            return true;
        }
        throw new BadVariableException();
    }




    /**
     * this private method checks whether the name of the variable is legal.
     * @param name - a name of a variable
     * @throws BadVariableException - if the name of the variable isnt legal
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

    /**
     * @return boolean that say if the file is final
     */
    public Boolean getFinal() {
        return isFinal;
    }

    /**
     * @return boolean that say if the file is initialize
     */
    public Boolean getInitialize() {
        return isInitialize;
    }
}
