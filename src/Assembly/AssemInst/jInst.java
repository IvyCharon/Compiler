package Assembly.AssemInst;

import java.util.LinkedHashSet;

import Assembly.AssemBlock;
import Assembly.Operand.Register;

public class jInst extends asmInst {
    public AssemBlock dest;

    public jInst(AssemBlock dest, AssemBlock b) {
        this.dest = dest;
        this.block = b;
    }
    
    @Override
    public String toString() {
        return "j " + dest.name;
    }

    @Override
    public void setStackImm(int s) {
        
    }
    
    @Override
    public LinkedHashSet<Register> use() {
        LinkedHashSet<Register> use = new LinkedHashSet<>();
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

    }

    @Override
    public void replaceDef(Register ori, Register rep) {
        
    }
}
