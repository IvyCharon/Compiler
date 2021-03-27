package Assembly.AssemInst;

import Assembly.Operand.AsmGlobalVar;
import Assembly.Operand.Register;
import Assembly.Operand.VirtualRegister;
import Assembly.Operand.asmOperand;

public class binaryInst extends asmInst {
    public Register rd, rs1;
    public asmOperand rs2;
    public String op;

    public binaryInst(String op, Register rd, Register rs1, asmOperand rs2) {
        this.op = op;
        this.rd = rd;
        this.rs1 = rs1;
        this.rs2 = rs2;
        if(rs1 instanceof VirtualRegister || rs1 instanceof AsmGlobalVar) UsedVirReg.add(rs1);
        if(rs2 instanceof VirtualRegister || rs2 instanceof AsmGlobalVar) UsedVirReg.add((Register)rs2);
        if(rd instanceof VirtualRegister || rd instanceof AsmGlobalVar) UsedVirReg.add(rd);
    }

    @Override
    public String toString() {
        return op + " " + rd.toString() + ", " + rs1.toString() + ", " + rs2.toString();
    }
}
