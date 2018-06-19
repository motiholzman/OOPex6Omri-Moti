package oop.ex6.main;

import oop.ex6.main.variables.BooleanVariable;

public class IfWhile extends Scope {


    /**
     * this constructor initialize the object.
     *
     * @param fatherScope : the outer scope of the constructed scope.
     * @param name
     */
    public IfWhile(Scope fatherScope, String name) {
        super(fatherScope, name);
    }

    /**
     * this method checks if the scope's signature is correspond to the arguments that appears in the
     * calling to the function.
     */
    public void checkSignature(String [] listOfArguments) throws IllegalCodeException {
        BooleanVariable booleanVariable = new BooleanVariable
                ("boolChecker","true",false,true,this);
        for(String param:listOfArguments){
            booleanVariable.checkVariable(param.trim(), );
        }

    }


}
