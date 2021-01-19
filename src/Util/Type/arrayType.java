package Util.Type;

public class arrayType extends Type {
    private Type type;
    private int dim;

    public arrayType(Type type, int dim) {
        this.type = type;
        this.dim = dim;
    }

    public int dim() {
        return dim;
    }

    public Type type() {
        return type;
    }
}