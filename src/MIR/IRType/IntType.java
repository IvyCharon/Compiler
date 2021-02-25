package MIR.IRType;

public class IntType extends IRBaseType {
    private int size;

    public IntType(int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return this.size;
    }
}
