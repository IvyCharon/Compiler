package MIR.IRType;

public class BoolType extends IRBaseType {
    private int size;

    public BoolType(int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return size;
    }
}
