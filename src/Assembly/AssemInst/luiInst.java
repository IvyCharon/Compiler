package Assembly.AssemInst;

import java.util.LinkedHashSet;

import Assembly.AssemBlock;
import Assembly.Operand.AsmGlobalVar;
import Assembly.Operand.Imm;
import Assembly.Operand.Register;

public class luiInst extends asmInst {
    public Register reg;
    public Imm imm;

    public luiInst(Register reg, Imm imm, AssemBlock b) {
        this.reg = reg;
        this.imm = imm;
        this.block = b;
    }

    @Override
    public String toString() {
        return "lui " + reg.toString() + ", " + imm.toString();
    }

    @Override
    public void setStackImm(int s) {
        if(imm.inStack)
            imm = new Imm(imm.val + s);
    }
    
    @Override
    public LinkedHashSet<Register> use() {
        LinkedHashSet<Register> use = new LinkedHashSet<>();
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

    }

    @Override
    public void replaceDef(Register ori, Register rep) {
        if(ori == reg) reg = rep;
    }
}
