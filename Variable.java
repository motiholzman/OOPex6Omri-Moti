public abstract class Variable {
    protected String type;
    protected String name;
    protected Boolean isInitialise;
    protected Boolean isFinal;

    public Variable(String type, String name, Boolean isInitialise, Boolean isFinal){
        this.type = type;
        this.name = name;
        this.isInitialise = isInitialise;
        this.isFinal = isFinal;
    }

    public Variable(Variable variable){
        this.type = variable.type;
        this.isInitialise = variable.isInitialise;
        this.name = variable.name;
        this.isFinal = variable.isFinal;
    }

    public abstract Boolean paramOk(String param);

    public abstract Variable copyValue(Variable variable);

}
