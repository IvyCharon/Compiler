package Assembly.AssemInst;

import Assembly.Operand.Imm;
import Assembly.Operand.VirtualRegister;

public class liInst extends asmInst {
    public VirtualRegister rd;
    public Imm imm;

    public liInst(VirtualRegister rd, Imm imm) {
        this.rd = rd;
        this.imm = imm;
        UsedVirReg.add(rd);
    }

    @Override
    public String toString() {
        return "li " + rd.toString() + ", " + imm.toString();
    }
}
