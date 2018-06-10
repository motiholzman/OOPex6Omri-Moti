package oop.ex6.main.variables;

public class DOUBLE extends Variable {

    public DOUBLE(String name, Boolean isInitialise, Boolean isFinal){
        super(name,isInitialise,isFinal);
        this.type = "double";
    }

    @Override
    public Boolean paramOk(String param) {
        try{
            double tryToParse = Double.parseDouble(param);
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
