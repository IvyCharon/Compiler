package Assembly.AssemInst;

import Assembly.Operand.Imm;
import Assembly.Operand.Register;

public class luiInst extends asmInst {
    public Register reg;
    public Imm imm;

    public luiInst(Register reg, Imm imm) {
        this.reg = reg;
        this.imm = imm;
    }

    @Override
    public String toString() {
        return "lui " + reg.toString() + ", " + imm.toString();
    }
}
