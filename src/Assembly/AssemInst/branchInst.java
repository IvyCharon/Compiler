package Assembly.AssemInst;

import java.util.LinkedHashSet;

import Assembly.AssemBlock;
import Assembly.Operand.*;

public class branchInst extends asmInst {
    public Register rs;
    public AssemBlock trueBlock, falseBlock;

    public branchInst(Register rs, AssemBlock trueB, AssemBlock falseB, AssemBlock b) {
        this.rs = rs;
        this.trueBlock = trueB;
        this.falseBlock = falseB;
        this.block = b;
    }

    @Override
    public String toString() {
        return "bnez " + rs.toString() + ", " + trueBlock.name + "\n\t" + "j " + falseBlock.name;
    }

    @Override
    public void setStackImm(int s) {
        
    }

    @Override
    public LinkedHashSet<Register> use() {
        LinkedHashSet<Register> use = new LinkedHashSet<>();
        if(!(rs instanceof AsmGlobalVar)) use.add(rs);
        return use;
    }

    @Override
    public LinkedHashSet<Register> def() {
        LinkedHashSet<Register> def = new LinkedHashSet<>();
        return def;
    }

    @Override
    public Register rd() {
        return null;
    }

    @Override
    public void replaceUse(Register ori, Register rep) {
        if(rs == ori) rs = rep;
    }

    @Override
    public void replaceDef(Register ori, Register rep) {
        
    }
}
