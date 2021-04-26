package Assembly.AssemInst;

import java.util.LinkedHashSet;

import Assembly.AssemBlock;
import Assembly.Operand.AsmGlobalVar;
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
    public void setStackImm(int s) {}
    
    @Override
    public LinkedHashSet<Register> use() {
        LinkedHashSet<Register> use = new LinkedHashSet<>();
        if(!(rs instanceof AsmGlobalVar)) use.add(rs);
        return use;
    }

    @Override
    public LinkedHashSet<Register> def() {
        LinkedHashSet<Register> def = new LinkedHashSet<>();
        if(!(rd instanceof AsmGlobalVar)) def.add(rd);
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
