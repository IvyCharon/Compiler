package MIR.IRType;

public class VoidType extends IRBaseType {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public String toString() {
        return "void";
    }
}
