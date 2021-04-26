package Assembly.AssemInst;

import java.util.LinkedHashSet;

import Assembly.AssemBlock;
import Assembly.Operand.AsmGlobalVar;
import Assembly.Operand.Imm;
import Assembly.Operand.Register;
public class liInst extends asmInst {
    public Register rd;
    public Imm imm;

    public liInst(Register rd, Imm imm, AssemBlock b) {
        this.rd = rd;
        this.imm = imm;
        this.block = b;
    }

    @Override
    public String toString() {
        return "li " + rd.toString() + ", " + imm.toString();
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
        if(!(rd instanceof AsmGlobalVar)) def.add(rd);
        return def;
    }

    @Override
    public Register rd() {
        return rd;
    }

    @Override
    public void replaceUse(Register ori, Register rep) {

    }

    @Override
    public void replaceDef(Register ori, Register rep) {
        if(ori == rd) rd = rep;
    }
}
