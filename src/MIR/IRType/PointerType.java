package MIR.IRType;

public class PointerType extends IRBaseType {
    public IRBaseType baseType;
    
    public PointerType(IRBaseType baseType) {
        this.baseType = baseType;
    }

    @Override
    public int size() {
        return 4;
    }

    @Override
    public String toString() {
        return baseType.toString() + "*";
    }
}
