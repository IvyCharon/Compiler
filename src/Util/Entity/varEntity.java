package Util.Entity;

import MIR.IROperand.operand;
import Util.Type.*;

public class varEntity extends Entity {
    private Type type;
    public boolean isGlobal, isMember;
    public operand oper;

    public varEntity(String name, Type t, boolean isGlobal) {
        super(name);
        this.type = t;
        this.isGlobal = isGlobal;
        this.isMember = false;
    }

    public Type type() {
        return type;
    }
}
