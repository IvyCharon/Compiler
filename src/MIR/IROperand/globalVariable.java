package MIR.IROperand;

import MIR.IRType.IRBaseType;

public class globalVariable extends operand {
    public String name;
    public operand init;

    public globalVariable(IRBaseType type) {
        super(type);
    }
}
