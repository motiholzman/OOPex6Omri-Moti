package oop.ex6.main.variables;

public class STRING extends Variable {

    public STRING(String name, Boolean isInitialise, Boolean isFinal){
        super(name,isInitialise,isFinal);
        this.type = "String";
    }

    @Override
    public Boolean paramOk(String param) {
        return true; // always good
    }

    @Override
    public Variable copyValue(Variable variable) {
        return null;
    }
}
