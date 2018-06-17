package oop.ex6.main;

import oop.ex6.main.variables.Variable;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * this class implements a Scope object
 */
public class Scope {

    /* the Scope's outer Scope. if it is null - this is the main Scope of the file. */
    private Scope OuterScope;

    /* the Scope's variables. this ArrayList include only the Scope's local variables */
    private ArrayList<Variable> variablesArray;

    /* the Scope's name. */
    private String name;


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
     */
    public void checkSignature(String parmList) throws BadScopeSignatureException, IllegalCodeException {

    }

    /**
     * @param variableName : a string represents a name of a variable.
     * @return: the most inner variable (relative to the scope's hierarchy) with that name.
     */
    public Variable getVariable (String variableName) {
        Scope currentScope = this;
        while (currentScope != null) {
            for (Variable variable : currentScope.variablesArray) {
                if (variable.getName().equals(variableName))
                    return variable;
            }
            currentScope = this.OuterScope;
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
}
