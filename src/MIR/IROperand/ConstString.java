package MIR.IROperand;

import MIR.IRType.IntType;
import MIR.IRType.PointerType;

public class ConstString extends operand {
    private String value;

    public ConstString(String value) {
        super(new PointerType(new IntType(32)));
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    @Override
    public boolean isConst() {
        return true;
    }

    @Override
    public String toString() {
        return value;
    }
}
