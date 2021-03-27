package Assembly.AssemInst;

import Assembly.Operand.Imm;
import Assembly.Operand.Register;

public class loadInst extends asmInst { //load addr + imm to reg
    public Register reg, addr;
    public Imm imm;
    public int size;

    public loadInst(Register reg, Register addr, Imm imm, int size) {
        this.reg = reg;
        this.addr = addr;
        this.imm = imm;
        this.size = size;
    }


    @Override
    public String toString() {
        return "lw " + reg.toString() + ", " + imm.toString() + "(" + addr.toString() + ")";
    }
}
