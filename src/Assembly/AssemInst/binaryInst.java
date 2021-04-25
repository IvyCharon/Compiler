package Assembly.AssemInst;

import java.util.LinkedHashSet;

import Assembly.AssemBlock;
import Assembly.Operand.Imm;
import Assembly.Operand.Register;
import Assembly.Operand.VirtualRegister;
import Assembly.Operand.asmOperand;

public class binaryInst extends asmInst {
    public Register rd, rs1;
    public asmOperand rs2;
    public String op;

    public binaryInst(String op, Register rd, Register rs1, asmOperand rs2, AssemBlock b) {
        this.op = op;
        this.rd = rd;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.block = b;
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

    @Override
    public LinkedHashSet<Register> use() {
        LinkedHashSet<Register> use = new LinkedHashSet<>();
        if(rs1 instanceof VirtualRegister) use.add(rs1);
        if(rs2 instanceof VirtualRegister) use.add((VirtualRegister)rs2);
        return use;
    }

    @Override
    public LinkedHashSet<Register> def() {
        LinkedHashSet<Register> def = new LinkedHashSet<>();
        if(rd instanceof VirtualRegister) def.add(rd);
        return def;
    }

    @Override
    public Register rd() {
        return rd;
    }

    @Override
    public void replaceUse(Register ori, Register rep) {
        if(rs1 == ori) rs1 = rep;
        if(rs2 == ori) rs2 = rep;
    }

    @Override
    public void replaceDef(Register ori, Register rep) {
        if(rd == ori) rd = rep;
    }
}
