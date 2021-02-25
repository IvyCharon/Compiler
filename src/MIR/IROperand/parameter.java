package MIR.IROperand;

import MIR.IRType.IRBaseType;

public class parameter extends operand {
    private String name;

    public parameter(IRBaseType type, String name) {
        super(type);
        this.name = name;
    }

    public String name() {
        return name;
    }
}
