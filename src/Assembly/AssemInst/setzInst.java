package Assembly.AssemInst;

import java.util.LinkedHashSet;

import Assembly.AssemBlock;
import Assembly.Operand.Register;
import Assembly.Operand.VirtualRegister;

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
    
    @Override
    public LinkedHashSet<Register> use() {
        LinkedHashSet<Register> use = new LinkedHashSet<>();
        if(rs instanceof VirtualRegister) use.add(rs);
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
        if(rs == ori) rs = rep;
    }

    @Override
    public void replaceDef(Register ori, Register rep) {
        if(rd == ori) rd = rep;
    }
}
