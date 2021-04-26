package Assembly.AssemInst;

import java.util.LinkedHashSet;

import Assembly.AssemBlock;
import Assembly.Operand.AsmGlobalVar;
import Assembly.Operand.Imm;
import Assembly.Operand.Register;

public class loadInst extends asmInst { //load addr + imm to reg
    public Register reg, addr;
    public Imm imm;

    public loadInst(Register reg, Register addr, Imm imm, AssemBlock b) {
        this.reg = reg;
        this.addr = addr;
        this.imm = imm;
        this.block = b;
    }


    @Override
    public String toString() {
        if(addr instanceof AsmGlobalVar)
            return "lw " + reg.toString() + ", " + addr.toString();
        else
            return "lw " + reg.toString() + ", " + imm.toString() + "(" + addr.toString() + ")";
    }

    @Override
    public void setStackImm(int s) {
        if(imm.inStack)
            imm = new Imm(imm.val + s);
    }
    
    @Override
    public LinkedHashSet<Register> use() {
        LinkedHashSet<Register> use = new LinkedHashSet<>();
        if(!(addr instanceof AsmGlobalVar)) use.add(addr);
        return use;
    }

    @Override
    public LinkedHashSet<Register> def() {
        LinkedHashSet<Register> def = new LinkedHashSet<>();
        if(!(reg instanceof AsmGlobalVar)) def.add(reg);
        return def;
    }

    @Override
    public Register rd() {
        return reg;
    }

    @Override
    public void replaceUse(Register ori, Register rep) {
        if(addr == ori) addr  =rep;
    }

    @Override
    public void replaceDef(Register ori, Register rep) {
        if(reg == ori) reg = rep;
    }
}
