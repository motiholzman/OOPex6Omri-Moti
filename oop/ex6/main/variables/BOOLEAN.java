package oop.ex6.main.variables;

public class BOOLEAN extends  Variable {

    public BOOLEAN(String name, Boolean isInitialise, Boolean isFinal){
        super(name,isInitialise,isFinal);
        this.type = "Boolean";
    }

    @Override
    public Boolean paramOk(String param) {
        try{
            if(param.equals("true") || param.equals("false")){
                return true;
            }
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
