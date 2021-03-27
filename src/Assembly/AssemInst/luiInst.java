package Assembly.AssemInst;

import Assembly.Operand.AsmGlobalVar;
import Assembly.Operand.Imm;
import Assembly.Operand.Register;
import Assembly.Operand.VirtualRegister;

public class luiInst extends asmInst {
    public Register reg;
    public Imm imm;

    public luiInst(Register reg, Imm imm) {
        this.reg = reg;
        this.imm = imm;
        if(reg instanceof VirtualRegister || reg instanceof AsmGlobalVar) UsedVirReg.add(reg);
    }

    @Override
    public String toString() {
        return "lui " + reg.toString() + ", " + imm.toString();
    }
}
