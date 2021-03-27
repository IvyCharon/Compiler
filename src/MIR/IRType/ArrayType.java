package MIR.IRType;

public class ArrayType extends IRBaseType {
    public IRBaseType baseType;
    private int size;
    public ArrayType(IRBaseType type, int size) {
        this.baseType = type;
        this.size = size;
    }

    @Override 
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return "i8*";
    }
}
