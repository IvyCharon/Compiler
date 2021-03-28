package Assembly.AssemInst;

import Assembly.AssemBlock;
import Assembly.Operand.Register;

public class mvInst extends asmInst {
    public Register rd, rs;

    public mvInst(Register rd, Register rs, AssemBlock b) {
        this.rd = rd;
        this.rs = rs;
        this.block = b;
    }

    @Override
    public String toString() {
        return "mv " + rd.toString() + ", " + rs.toString();
    }

    @Override
    public void setStackImm(int s) {
        
    }
}
