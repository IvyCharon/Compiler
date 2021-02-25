package MIR.IRType;

public class StringType extends IRBaseType {
    private int size;

    public StringType(int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return this.size;
    }
}
