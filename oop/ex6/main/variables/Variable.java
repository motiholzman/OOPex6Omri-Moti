package oop.ex6.main.variables;

import oop.ex6.main.BadCodeException;
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

    /** the Variable's value*/
    protected String value;

    /** indicates whether the variable is initialize.*/
    protected Boolean isInitialize;

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
        variableScope = currentScope;
        try{
            checkName(name);
            if(isInitialize) {
                checkAndAssignVariable(value, this.variableScope);
            }
        }
        catch (BadVariableException e) {
            throw new IllegalCodeException();
        }
    }



    /**
     * this method checks if the variable is initialize correctly according to it's definitions.
     * @param value : a value to assign.
     * @param currentScope : the scope we work in. to find out if the is a variable who's name is "value".
     * @throws BadVariableException: in case there was a problem in the initialization.
     */
    public abstract void checkAndAssignVariable(String value, Scope currentScope) throws BadVariableException;

    /**
     * this method checks if there is a variable with the given name in this scope or some other scope, and
     * whether this assignment is legal: the type of the variables are match, and the assigned variable is
     * initialize.
     * @param variableName : a given name for search.
     * @param currentScope : the scope to search in.
     * @return : true if the assignment is legal, false if the variable wasn't found.
     * @throws BadVariableException: in case that the assignment isn't legal.
     */
    protected Variable isVariableAssignmentValid(String variableName, Scope currentScope) throws
            BadVariableException {
        Variable otherVariable = variableScope.getVariable(variableName, currentScope);
        if (otherVariable == null) {
            return null;
        }
        if ((otherVariable.isInitialize)) {
            checkAndAssignVariable(otherVariable.value, currentScope);
            return otherVariable;
        }
        throw new BadVariableException();
    }




    /**
     * this private method checks whether the name of the variable is legal.
     * @param name - a name of a variable
     * @throws BadVariableException - if the name of the variable isnt legal
     */
    private void checkName(String name) throws IllegalCodeException {
        if (variableScope.searchForNameInVariablesList(name)) {
            throw new BadCodeException("Error: two arguments with same name in the same scope.");
        }
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
