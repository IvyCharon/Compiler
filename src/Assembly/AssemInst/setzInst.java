package Assembly.AssemInst;

import Assembly.AssemBlock;
import Assembly.Operand.Register;

public class setzInst extends asmInst {
    public String op;
    public Register rd, rs;

    public setzInst(String op, Register rd, Register rs, AssemBlock ab) {
        this.block = ab;
        this.op = op;
        this.rd = rd;
        this.rs = rs;
    }

    @Override
    public String toString() {
        return op + " " + rd.toString() + ", " + rs.toString();
    }

    @Override
    public void setStackImm(int s) {}
}
