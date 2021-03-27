package MIR.IROperand;

import MIR.IRType.IRBaseType;

public class Register extends operand {
    public String name;

    public Register(IRBaseType type, String name) {
        super(type);
        this.name = name;
    }

    @Override
    public boolean isConst() {
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
