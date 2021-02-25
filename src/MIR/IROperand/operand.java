package MIR.IROperand;

import MIR.IRType.IRBaseType;

public abstract class operand {
    private IRBaseType type;

    public operand(IRBaseType type) {
        this.type = type;
    }

    public IRBaseType type() {
        return type;
    }
}
