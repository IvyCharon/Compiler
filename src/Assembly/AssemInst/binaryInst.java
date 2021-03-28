package Assembly.AssemInst;

import Assembly.Operand.Imm;
import Assembly.Operand.Register;
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
    }

    @Override
    public String toString() {
        return op + " " + rd.toString() + ", " + rs1.toString() + ", " + rs2.toString();
    }

    @Override
    public void setStackImm(int s) {
        if(rs2 instanceof Imm && ((Imm)rs2).inStack)
            rs2 = new Imm(((Imm)rs2).val + s);
    }
}
