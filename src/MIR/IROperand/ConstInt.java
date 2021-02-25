package MIR.IROperand;

import MIR.IRType.IntType;

public class ConstInt extends operand {
    private int value;

    public ConstInt(int size, int value) {
        super(new IntType(size));
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
