package MIR.IROperand;

import MIR.IRType.*;

public class ConstNull extends operand {
    public ConstNull() {
        super(new NullType());
    }

    @Override
    public boolean isConst() {
        return true;
    }

    @Override
    public String toString() {
        return null;
    }
}
