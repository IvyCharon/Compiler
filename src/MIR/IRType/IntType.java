package MIR.IRType;

import MIR.IROperand.*;

public class IntType extends IRBaseType {
    private int size;

    public IntType(int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public operand toOper() {
        return new ConstInt(32, 0);
    }

    @Override
    public String toString() {
        return "i32";
    }
}
