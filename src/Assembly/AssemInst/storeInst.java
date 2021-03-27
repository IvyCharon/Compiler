package Assembly.AssemInst;

import Assembly.Operand.AsmGlobalVar;
import Assembly.Operand.Imm;
import Assembly.Operand.Register;
import Assembly.Operand.VirtualRegister;

public class storeInst extends asmInst {
    public Register reg, addr;
    public Imm imm;
    public int size;

    public storeInst(Register reg, Register addr, Imm imm, int size) {
        this.reg = reg;
        this.addr = addr;
        this.imm = imm;
        this.size = size;
        if(reg instanceof VirtualRegister || reg instanceof AsmGlobalVar) UsedVirReg.add(reg);
        if(addr instanceof VirtualRegister || addr instanceof AsmGlobalVar) UsedVirReg.add(addr);
    }

    @Override
    public String toString() {
        return "sw " + reg.toString() + ", " + imm.toString() + "(" + addr.toString() + ")";
    }
}
