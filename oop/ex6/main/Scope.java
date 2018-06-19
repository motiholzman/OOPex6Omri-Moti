package oop.ex6.main;

import oop.ex6.main.variables.Variable;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * this class implements a Scope object
 */
public class Scope {

    /* the Scope's outer Scope. if it is null - this is the main Scope of the file. */
    private final Scope OuterScope;

    /* the Scope's variables. this ArrayList include only the Scope's local variables */
    private ArrayList<Variable> variablesArray;

    /* the Scope's name. */
    private final String name;

    /* the number of args this scope receives in the signature. */
    private int numberOfArgsInSignature;

    /* represents the empty string */
    private static final String EMPTY_STRING = "";


    /**
     * this constructor initialize the object.
     * @param fatherScope : the outer scope of the constructed scope.
     */
    public Scope (Scope fatherScope,String name) {
        this.OuterScope = fatherScope;
        this.name = name;
        variablesArray = new ArrayList<>();
    }

    /**
     * this method checks if the scope's signature is correspond to the arguments that appears in the
     * calling to the function.
     * @param listOfArguments : the arguments that was gave to the function when it was called.
     * @param currentScope : the current scope to search the variable in.
     * @throws IllegalCodeException: in case the arguments doesn't correspond to the function signature.
     * */
    public void checkSignature(String[] listOfArguments, Scope currentScope) throws IllegalCodeException {
        // this is an iterator of the variables of the scopes.
        Iterator<Variable> variablesIterator = variablesArray.iterator();
        if (!variablesIterator.hasNext() && listOfArguments.length > 0 &&
                listOfArguments[0].equals(Scope.EMPTY_STRING)) {
            return;
        }
        for (int i = 0; i < listOfArguments.length; i++) {
            if (!variablesIterator.hasNext() || i > this.numberOfArgsInSignature) {
                // if we got more variables in the function's call or less variables in the scope at all
                throw new BadCodeException("Error: too many arguments were given to the method.");
            }
            try {
                Variable currentScopeVariable = variablesIterator.next();
                currentScopeVariable.checkVariable(listOfArguments[i], currentScope);
            }
            catch (BadVariableException e) {
                throw new BadCodeException(
                        "Error: the arguments doesn't correspond to the function signature");
            }
        }
    }

    /**
     * @param variableName : a string represents a name of a variable.
     * @param searchScope
     * @return: the most inner variable (relative to the scope's hierarchy) with that name.
     */
    public Variable getVariable(String variableName, Scope searchScope) {
        Scope currentScope = searchScope;
        while (currentScope != null) {
            for (Variable variable : currentScope.variablesArray) {
                if (variable.getName().equals(variableName))
                    return variable;
            }
            currentScope = currentScope.OuterScope;
        }
        return null;
    }

    /**
     * add a variable to the Variable's array.
     * @param variable : a variable to add.
     */
    public void addVariable(Variable variable)  {
        // we add variable only if it have correct values and valid name
        variablesArray.add(variable);
    }

    /**
     * @return : returns the name of the scope.
     */
    public String getName() {
        return name;
    }

    /**
     * @return : the outer scope.
     */
    public Scope getOuterScope() {
        return OuterScope;
    }

    /**
     * this method checks if there is another variable in this scope with the given name.
     * @param name: a name to search
     * @return : true if the name exists, false otherwise.
     */
    public boolean searchForNameInVariablesList(String name) {
        for (Variable variable : variablesArray) {
            if (variable.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param numberOfArgsInSignature : set the number of args in the signature.
     */
    public void setNumberOfArgsInSignature(int numberOfArgsInSignature) {
        this.numberOfArgsInSignature = numberOfArgsInSignature;
    }
}
