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
}
