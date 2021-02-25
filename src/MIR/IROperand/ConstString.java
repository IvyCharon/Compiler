package MIR.IROperand;

import MIR.IRType.StringType;

public class ConstString extends operand {
    private String value;

    public ConstString(int size, String value) {
        super(new StringType(size));
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
