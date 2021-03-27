package Assembly.AssemInst;

import Assembly.Operand.AsmGlobalVar;
import Assembly.Operand.Register;
import Assembly.Operand.VirtualRegister;

public class mvInst extends asmInst {
    public Register rd, rs;

    public mvInst(Register rd, Register rs) {
        this.rd = rd;
        this.rs = rs;
        if(rd instanceof VirtualRegister || rd instanceof AsmGlobalVar) UsedVirReg.add(rd);
        if(rs instanceof VirtualRegister || rs instanceof AsmGlobalVar) UsedVirReg.add(rs);
    }

    @Override
    public String toString() {
        return "mv " + rd.toString() + ", " + rs.toString();
    }
}
