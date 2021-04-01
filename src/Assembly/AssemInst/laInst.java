package Assembly.AssemInst;

import Assembly.Operand.Register;

public class laInst extends asmInst {
    public Register rd, addr;

    public laInst(Register rd, Register addr) {
        this.rd = rd;
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "la " + rd.toString() + ", " + addr.toString();
    }

    @Override
    public void setStackImm(int s) {}
}
