package oop.ex6.main;

import oop.ex6.main.variables.Variable;

import java.util.HashSet;
import java.util.regex.Matcher;

/**
 * this class implements a Scope object
 */
public class Scope {

    /* the Scope's outer Scope. if it is null - this is the main Scope of the file. */
    private Scope OuterScope;

    /* the Scope's variables. this set include only the Scope's local variables */
    private HashSet <Variable> variablesHashSet;

    private String [] parametersType;

    private String name;


    // at the
    // parser.

    /**
     * this constructor initialize the object.
     * @param fatherScope : the outer scope of the constructed scope.
     */
    public Scope (Scope fatherScope,String name) {
        this.OuterScope = fatherScope;
        this.parametersType = null;
        this.name = name;
        variablesHashSet = new HashSet<>();
    }

//    /**
//     * this method checks if the scope's signature is correct.
//     */
//    public void checkSignature() throws BadScopeSignatureException;

    /**
     * @param variableName : a string represents a name of a variable.
     * @return: the most inner variable (relative to the scope's hierarchy) with that name.
     */
    public Variable getVariable (String variableName) {
        Scope currentScope = this;
        while (currentScope != null) {
            for (Variable variable : currentScope.variablesHashSet) {
                if (variable.getName().equals(variableName))
                    return variable;
            }
            currentScope = this.OuterScope;
        }
        return null;
    }


    public void setParameters(String [] parametersType){
        this.parametersType = parametersType;
    }

    public void addVariable(Variable variable)  {
        // we add variable only if it have correct values and valid name
        variablesHashSet.add(variable);
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
