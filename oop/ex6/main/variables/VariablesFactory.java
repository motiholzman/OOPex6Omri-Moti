package oop.ex6.main.variables;

import oop.ex6.main.IllegalCodeException;
import oop.ex6.main.Scope;

/**
 * this class represents a Factory of variables.
 */
public class VariablesFactory  {

    /* a constants represents types of variables. */
    private static final String INT = "int",DOUBLE = "double", STRING = "String", BOOLEAN ="boolean",
            CHAR="char";

    /**
     * this method adds a variable to the scope
     * @param type the type of the variable
     * @param variableFinal if the variable is final
     * @param variableName the variable name
     * @param variableValue the variable value
     * @param scope the scope we want to add the value to
     * @param variableInitiated if the value is initiated
     * @throws IllegalCodeException : in case this is not a valid variable.
     */
    public static void createVariable(String type, Boolean variableFinal, String variableName, String
            variableValue, Scope scope, Boolean variableInitiated) throws IllegalCodeException {
        Variable variable;
        if (type.equals(INT)) {
            variable = new IntVariable(variableName, variableValue, variableFinal, variableInitiated, scope);
        } else if (type.equals(DOUBLE)) {
            variable = new DoubleVariable(variableName, variableValue, variableFinal, variableInitiated,
                    scope);
        } else if (type.equals(CHAR)) {
            variable = new CharVariable(variableName, variableValue, variableFinal, variableInitiated,
                    scope);
        } else if (type.equals(STRING)) {
            variable = new StringVariable(variableName, variableValue, variableFinal, variableInitiated,
                    scope);
        } else if (type.equals(BOOLEAN)) {
            variable = new BooleanVariable(variableName, variableValue, variableFinal, variableInitiated,
                    scope );
        } else {// not a valid variable
            throw new IllegalCodeException();
        }
        scope.addVariable(variable);
    }
}
