package oop.ex6.main.variables;

public class CHAR extends Variable {

    public CHAR(String name, Boolean isInitialise, Boolean isFinal){
        super(name,isInitialise,isFinal);
        this.type = "char";
    }

    @Override
    public Boolean paramOk(String param) {
        return param.length() == 1;
    }

    @Override
    public Variable copyValue(Variable variable) {
        return null;
    }
}
