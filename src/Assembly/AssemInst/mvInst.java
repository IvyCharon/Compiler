package Assembly.AssemInst;

import Assembly.Operand.Register;

public class mvInst extends asmInst {
    public Register rd, rs;

    public mvInst(Register rd, Register rs) {
        this.rd = rd;
        this.rs = rs;
    }

    @Override
    public String toString() {
        return "mv " + rd.toString() + ", " + rs.toString();
    }

    @Override
    public void setStackImm(int s) {
        
    }
}
