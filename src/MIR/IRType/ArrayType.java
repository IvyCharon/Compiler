package MIR.IRType;

public class ArrayType extends IRBaseType {
    public IRBaseType baseType;
    private int len;
    public ArrayType(IRBaseType type, int len) {
        this.baseType = type;
        this.len = len;
    }

    @Override 
    public int size() {
        return baseType.size() * len;
    }

    @Override
    public String toString() {
        return "[" + len + " x " + baseType.toString() + "]";
    }
}
