package MIR.IRType;

import MIR.IROperand.ConstNull;
import MIR.IROperand.operand;

public abstract class IRBaseType {
    public abstract int size();
    public operand toOper() {
        return new ConstNull();
    }

    public abstract String toString();
}
