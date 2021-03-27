package MIR.IROperand;

import MIR.IRType.IRBaseType;

public class globalVariable extends operand {
    public String name;
    public operand init;

    public globalVariable(IRBaseType type, String name, operand init) {
        super(type);
        this.name = name;
        this.init = init;
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
