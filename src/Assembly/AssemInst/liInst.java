package Assembly.AssemInst;

import Assembly.Operand.Imm;
import Assembly.Operand.Register;

public class liInst extends asmInst {
    public Register rd;
    public Imm imm;

    public liInst(Register rd, Imm imm) {
        this.rd = rd;
        this.imm = imm;
    }

    @Override
    public String toString() {
        return "li " + rd.toString() + ", " + imm.toString();
    }

    @Override
    public void setStackImm(int s) {
        if(imm.inStack)
            imm = new Imm(imm.val + s);
    }
}
