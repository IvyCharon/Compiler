package Assembly.AssemInst;

import Assembly.AssemBlock;
import Assembly.Operand.Imm;
import Assembly.Operand.Register;

public class luiInst extends asmInst {
    public Register reg;
    public Imm imm;

    public luiInst(Register reg, Imm imm, AssemBlock b) {
        this.reg = reg;
        this.imm = imm;
        this.block = b;
    }

    @Override
    public String toString() {
        return "lui " + reg.toString() + ", " + imm.toString();
    }

    @Override
    public void setStackImm(int s) {
        if(imm.inStack)
            imm = new Imm(imm.val + s);
    }
}
