package Assembly.Operand;

public class AsmGlobalVar extends Register {
    public String name;

    public int intVal;
    public boolean boolVal;
    public String stringVal;

    public enum type {Int, Bool, String};

    public type gvType;

    public AsmGlobalVar(String name) {
        this.name = name;
        this.gvType = null;
    }

    public AsmGlobalVar(String name, int intVal) {
        this.name = name;
        this.intVal = intVal;
        this.gvType = type.Int;
    }

    public AsmGlobalVar(String name, boolean boolVal) {
        this.name = name;
        this.boolVal = boolVal;
        this.gvType = type.Bool;
    }

    public AsmGlobalVar(String name, String stringVal) {
        this.name = name;
        this.stringVal = stringVal.substring(1, stringVal.length() - 1);
        this.gvType = type.String;
    }

    @Override
    public String toString() {
        return name;
    }

}
