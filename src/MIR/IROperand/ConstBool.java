package MIR.IROperand;

import MIR.IRType.BoolType;

public class ConstBool extends operand {
    private boolean value;

    public ConstBool(int size, boolean value) {
        super(new BoolType(size));
        this.value = value;
    }

    public boolean value() {
        return this.value;
    }

    @Override
    public boolean isConst() {
        return true;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
