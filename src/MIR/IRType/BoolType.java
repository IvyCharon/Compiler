package MIR.IRType;

import MIR.IROperand.ConstBool;
import MIR.IROperand.operand;

public class BoolType extends IRBaseType {
    private int size;

    public BoolType(int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public operand toOper() {
        return new ConstBool(1, false);
    }

    @Override
    public String toString() {
        return "i1";
    }
}
