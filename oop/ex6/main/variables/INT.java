package oop.ex6.main.variables;

public class INT extends Variable {

    public INT(String name, Boolean isInitialise, Boolean isFinal){
        super(name,isInitialise,isFinal);
        this.type = "int";
    }

    @Override
    public Boolean paramOk(String param) {
        try{
            int tryToParse = Integer.parseInt(param);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }

    }

    @Override
    public Variable copyValue(Variable variable) {
        return null;
    }
}
