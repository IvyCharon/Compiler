package MIR.IRType;

public class NullType extends IRBaseType {
    public NullType() {}
    
    @Override
    public int size() {
        return 0;
    }

    @Override
    public String toString() {
        return "null";
    }
}
