package oop.ex6.main;

import oop.ex6.main.variables.BooleanVariable;

public class IfWhileScope extends Scope {


    /**
     * this constructor initialize the if or while scope.
     * @param fatherScope : the outer scope of the constructed scope.
     * @param name - if or while
     */
    public IfWhileScope(Scope fatherScope, String name) {
        super(fatherScope, name);
    }


    /**
     * this method checks if the scope's signature is correspond to the arguments that appears in the
     * calling to the function. which are booleans
     * @param listOfArguments : the arguments that was gave to the function when it was called.
     * @param currentScope
     * @throws IllegalCodeException - if the arguments arent legal
     */
    public void checkSignature(String[] listOfArguments, Scope currentScope) throws IllegalCodeException {
        BooleanVariable booleanVariable = new BooleanVariable
                ("boolChecker","true",false,true,this);
        for(String param:listOfArguments){
            booleanVariable.checkAndAssignVariable(param.trim(), currentScope);
        }

    }


}
