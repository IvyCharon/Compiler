package MIR.IROperand;

import MIR.IRType.IRBaseType;

public abstract class operand {
    private IRBaseType type;
    public boolean isArray = false;

    public operand(IRBaseType type) {
        this.type = type;
    }

    public IRBaseType type() {
        return type;
    }

    public abstract boolean isConst();

    public abstract String toString();
}
