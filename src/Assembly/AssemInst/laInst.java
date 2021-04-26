package Assembly.AssemInst;

import java.util.LinkedHashSet;

import Assembly.Operand.AsmGlobalVar;
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
    
    @Override
    public LinkedHashSet<Register> use() {
        LinkedHashSet<Register> use = new LinkedHashSet<>();
        if(!(addr instanceof AsmGlobalVar)) use.add(addr);
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
        if(addr == ori) addr = rep;
    }

    @Override
    public void replaceDef(Register ori, Register rep) {
        if(rd == ori) rd = rep;
    }
}
