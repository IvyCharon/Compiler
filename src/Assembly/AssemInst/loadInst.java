package Assembly.AssemInst;

import Assembly.AssemBlock;
import Assembly.Operand.Imm;
import Assembly.Operand.Register;

public class loadInst extends asmInst { //load addr + imm to reg
    public Register reg, addr;
    public Imm imm;
    public int size;

    public loadInst(Register reg, Register addr, Imm imm, int size, AssemBlock b) {
        this.reg = reg;
        this.addr = addr;
        this.imm = imm;
        this.size = size;
        this.block = b;
    }


    @Override
    public String toString() {
        return "lw " + reg.toString() + ", " + imm.toString() + "(" + addr.toString() + ")";
    }

    @Override
    public void setStackImm(int s) {
        if(imm.inStack)
            imm = new Imm(imm.val + s);
    }
}
